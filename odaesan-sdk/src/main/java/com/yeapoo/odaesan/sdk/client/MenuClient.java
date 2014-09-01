package com.yeapoo.odaesan.sdk.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.ErrorResponse;
import com.yeapoo.odaesan.sdk.model.menu.Button;
import com.yeapoo.odaesan.sdk.model.menu.ButtonContainer;
import com.yeapoo.odaesan.sdk.model.menu.ClickButton;
import com.yeapoo.odaesan.sdk.model.menu.ViewButton;

@Component
public class MenuClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(MenuClient.class);

    @Value("${wx.menu.create}")
    private String createURL;
    @Value("${wx.menu.get}")
    private String getURL;
    @Value("${wx.menu.delete}")
    private String deleteURL;

    public boolean createMenu(Authorization authorization, List<Button> buttonList) {
        Map<String, List<Button>> menu = new HashMap<String, List<Button>>();
        menu.put("button", buttonList);
        HttpEntity<Map<String, List<Button>>> request = new HttpEntity<Map<String, List<Button>>>(menu, headers);
        String response = template.postForObject(createURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            ErrorResponse error = mapper.readValue(response, ErrorResponse.class);
            int errcode =  error.getErrorCode();
            return errcode == 0;
        } catch (Exception e) {
            throw new WeixinSDKException(response, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Button> getMenu(Authorization authorization) throws WeixinSDKException {
        String response = template.getForObject(getURL, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            Map<String, Object> menuMapMap = mapper.readValue(response, Map.class);
            Map<String, Object> buttonListMap = MapUtil.get(menuMapMap, "menu", Map.class);

            List<Map<String, Object>> buttonMapList = MapUtil.get(buttonListMap, "button", List.class);
            List<Button> buttonList = new ArrayList<Button>();
            for (Map<String, Object> buttonMap : buttonMapList) {
                String type = MapUtil.get(buttonMap, "type");
                if (null == type) {
                    String name = MapUtil.get(buttonMap, "name");
                    ButtonContainer button = new ButtonContainer(name);
                    List<Map<String, Object>> subButtonMapList = MapUtil.get(buttonMap, "sub_button", List.class);
                    for (Map<String, Object> subButtonMap : subButtonMapList) {
                        button.addSubButton(generateButton(subButtonMap));
                    }
                    buttonList.add(button);
                } else {
                    buttonList.add(generateButton(buttonMap));
                }
            }
            return buttonList;
        } catch (Exception e) {
            throw new WeixinSDKException(response, e);
        }
    }

    public boolean deleteMenu(Authorization authorization) {
        String response = template.getForObject(getURL, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            ErrorResponse error = mapper.readValue(response, ErrorResponse.class);
            int errcode =  error.getErrorCode();
            return errcode == 0;
        } catch (Exception e) {
            throw new WeixinSDKException(response, e);
        }
    }

    private Button generateButton(Map<String, Object> buttonMap) {
        Button button = null;
        String type = MapUtil.get(buttonMap, "type");
        if ("click".equals(type)) {
            button = new ClickButton(MapUtil.get(buttonMap, "name"), MapUtil.get(buttonMap, "key"));
        } else if ("view".equals(type)) {
            button = new ViewButton(MapUtil.get(buttonMap, "name"), MapUtil.get(buttonMap, "url"));
        }
        return button;
    }
}
