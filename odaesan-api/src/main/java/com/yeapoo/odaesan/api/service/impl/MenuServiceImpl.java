package com.yeapoo.odaesan.api.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.MenuDao;
import com.yeapoo.odaesan.api.service.MenuService;
import com.yeapoo.odaesan.api.service.support.AppInfoProvider;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.sdk.client.MenuClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.menu.Button;
import com.yeapoo.odaesan.sdk.model.menu.ButtonContainer;
import com.yeapoo.odaesan.sdk.model.menu.ClickButton;
import com.yeapoo.odaesan.sdk.model.menu.ViewButton;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private MenuClient menuClient;

    private Method getMenu = ReflectionUtils.findMethod(MenuClient.class, "getMenu", new Class<?>[] {Authorization.class});
    private Method createMenu = ReflectionUtils.findMethod(MenuClient.class, "createMenu", new Class<?>[] {Authorization.class, List.class});

    @Override
    public boolean hasFetched(String infoId) {
        int count = menuDao.count(infoId);
        return count > 0;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public void fetchInfo(String infoId) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);

        Object result = adapter.invoke(menuClient, getMenu, new Object[] {null}, appInfo);
        if (null != result) {
            List<Button> buttonList = List.class.cast(result);
            int i = 0;
            for (Button button : buttonList) {
                i++;
                if (button instanceof ButtonContainer) {
                    handleButtonContainer(infoId, ButtonContainer.class.cast(button), i);
                } else if (button instanceof ClickButton) {
                    handleClickButton(infoId, "0", ClickButton.class.cast(button), i);
                } else if (button instanceof ViewButton) {
                    handleViewButton(infoId, "0", ViewButton.class.cast(button), i);
                }
            }
        }
    }

    @Transactional
    @Override
    public void reset(String infoId) {
        menuDao.truncate(infoId);
    }

    @Override
    public void publish(String infoId) {
        List<Button> buttonList = new ArrayList<Button>();
        List<Map<String, Object>> list = menuDao.findAllByParentId(infoId, "0");
        if (null != list) {
            for (Map<String, Object> map : list) {
                String id = MapUtil.get(map, "id");
                String name = MapUtil.get(map, "name");
                List<Map<String, Object>> subList = menuDao.findAllByParentId(infoId, id);
                if (null != subList && !subList.isEmpty()) {
                    ButtonContainer container = new ButtonContainer(name);
                    for (Map<String, Object> subMap : subList) {
                        container.addSubButton(generateButton(subMap));
                    }
                    buttonList.add(container);
                } else {
                    buttonList.add(generateButton(map));
                }
            }
        }

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        adapter.invoke(menuClient, createMenu, new Object[] {null, buttonList}, appInfo);
    }

    @Transactional
    @Override
    public String save(String infoId, Map<String, Object> itemMap) {
        String parentId = MapUtil.get(itemMap, "parent_id");
        String name = MapUtil.get(itemMap, "name");
        int sequence = MapUtil.get(itemMap, "sequence", Number.class).intValue();
        return menuDao.insert(infoId, parentId, name, sequence);
    }

    @Override
    public List<Map<String, Object>> list(String infoId) {
        List<Map<String, Object>> list = menuDao.listByParentId(infoId, "0");
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String id = MapUtil.get(map, "id");
                List<Map<String, Object>> subList = menuDao.listByParentId(infoId, id);
                if (null != subList && !subList.isEmpty()) {
                    map.put("sub_button", subList);
                }
            }
        }
        return list;
    }

    //XXX 此处对菜单顺序的更新不合理
    @Transactional
    @Override
    public void update(String infoId, String id, Map<String, Object> itemMap) {
        String name = MapUtil.get(itemMap, "name");
        int targetSequence = MapUtil.get(itemMap, "sequence", Number.class).intValue();
        Map<String, Object> parentIdAndSequence = menuDao.getParentIdAndSequenceById(infoId, id);
        int originalSequence = MapUtil.get(parentIdAndSequence, "sequence", Number.class).intValue();
        if (targetSequence != originalSequence) {
            // 先更新原有的
            String parentId = MapUtil.get(parentIdAndSequence, "parent_id");
            menuDao.updateByParentIdAndSequence(infoId, parentId, originalSequence, targetSequence);
        }
        // 再更新当前的
        menuDao.update(infoId, id, name, targetSequence);
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        menuDao.delete(infoId, id);
    }

    @Transactional
    @Override
    public void bindReply(String infoId, String menuId, String replyId, String replyType) {
        if (replyType.equals("link")) {
            String url = replyId;
            menuDao.bindViewReply(infoId, menuId, url);
        } else {
            String keycode = UUID.randomUUID().toString();
            menuDao.bindClickReply(infoId, menuId, keycode, replyId, replyType);
        }
    }

    @Transactional
    @Override
    public void unbindReply(String infoId, String menuId) {
        menuDao.unbindReply(infoId, menuId);
    }

    private void handleButtonContainer(String infoId, ButtonContainer buttonContainer, int sequence) {
        String name = buttonContainer.getName();
        String parentId = menuDao.insert(infoId, "0", name, sequence);
        List<Button> subButtons = buttonContainer.getSubButtons();
        int i = 0;
        for (Button button : subButtons) {
            i++;
            if (button instanceof ClickButton) {
                handleClickButton(infoId, parentId, ClickButton.class.cast(button), i);
            } else if (button instanceof ViewButton) {
                handleViewButton(infoId, parentId, ViewButton.class.cast(button), i);
            }
        }
    }

    private void handleClickButton(String infoId, String parentId, ClickButton button, int sequence) {
        String name = button.getName();
        String type = button.getType();
        String keycode = button.getKey();
        menuDao.insert(infoId, parentId, name, type, keycode, null, sequence);
    }

    private void handleViewButton(String infoId, String parentId, ViewButton button, int sequence) {
        String name = button.getName();
        String type = button.getType();
        String url = button.getUrl();
        menuDao.insert(infoId, parentId, name, type, null, url, sequence);
    }

    private Button generateButton(Map<String, Object> map) {
        String type = MapUtil.get(map, "type");
        String name = MapUtil.get(map, "name");
        Button button = null;
        if (Button.Type.CLICK.equals(type)) {
            String keycode = MapUtil.get(map, "keycode");
            button = new ClickButton(name, keycode);
        } else if (Button.Type.VIEW.equals(type)) {
            String url = MapUtil.get(map, "url");
            button = new ViewButton(name, url);
        }
        return button;
    }

}
