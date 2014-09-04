package com.yeapoo.odaesan.api.service.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.UserDao;
import com.yeapoo.odaesan.api.dao.UserGroupDao;
import com.yeapoo.odaesan.api.dao.UserGroupMappingDao;
import com.yeapoo.odaesan.api.service.UserGroupService;
import com.yeapoo.odaesan.api.service.support.AppInfoProvider;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.sdk.client.GroupClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Group;
import com.yeapoo.odaesan.sdk.model.GroupContainer;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupDao groupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserGroupMappingDao mappingDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private GroupClient groupClient;

    private Method getGroup = ReflectionUtils.findMethod(GroupClient.class, "getGroup", new Class<?>[] {Authorization.class});

    @Transactional
    @Override
    public void init(String infoId) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);
        Object result = adapter.invoke(groupClient, getGroup, new Object[] {null}, appInfo);

        Assert.notNull(result);
        GroupContainer container = GroupContainer.class.cast(result);
        List<Group> groups = container.getGroups();
        groupDao.batchInsert(infoId, groups);
    }

    @Transactional
    @Override
    public String save(String infoId, String name) {
        return groupDao.insert(infoId, name);
    }

    @Override
    public List<Map<String, Object>> list(String infoId) {
        List<Map<String, Object>> list = groupDao.list(infoId);
        Integer ungroupedCount = userDao.count(infoId, Constants.UserGroup.UNGROUPED_ID);
        if (0 != ungroupedCount) {
            for (Map<String, Object> map : list) {
                String id = MapUtil.get(map, "id");
                if (Constants.UserGroup.UNGROUPED_ID.equals(id)) {
                    map.put("num", ungroupedCount);
                }
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void update(String infoId, String id, String name) {
        groupDao.update(infoId, id, name);
    }

    @Transactional
    @Override
    public void delete(String infoId, String groupId) {
        // 获取分组下面现有的用户
        List<String> openidList = mappingDao.findOpenidByGroupId(infoId, groupId);
        // 删除分组下面现有的用户
        mappingDao.deleteByGroupId(infoId, groupId);

        // 处理现有用户，判断是否需要进行未分组操作
        Iterator<String> iterator = openidList.iterator();
        while (iterator.hasNext()) {
            String openid = iterator.next();
            List<Map<String, Object>> list = mappingDao.findByOpenid(infoId, openid);
            if (!list.isEmpty()) {
                iterator.remove();
            }
        }
        userDao.batchUpdateUngrouped(infoId, openidList, true);

        // 删除分组
        groupDao.delete(infoId, groupId);
    }
}
