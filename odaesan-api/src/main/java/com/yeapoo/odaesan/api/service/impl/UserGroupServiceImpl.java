package com.yeapoo.odaesan.api.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
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
    private UserGroupMappingDao mappingDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private GroupClient groupClient;

    private Method getGroup = ReflectionUtils.findMethod(GroupClient.class, "getGroup", new Class<?>[] {Authorization.class});

    private Map<String, Map<String, String>> cache = new ConcurrentHashMap<String, Map<String,String>>();

    @Transactional
    @Override
    public void init(String infoId) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);
        Object result = adapter.invoke(groupClient, getGroup, new Object[] {null}, appInfo);

        Assert.notNull(result);
        GroupContainer container = GroupContainer.class.cast(result);
        List<Group> groups = container.getGroups();
        groupDao.batchInsert(infoId, groups);
        groupDao.insert(infoId, Constants.UserGroup.ALL_ID, Constants.UserGroup.ALL);
    }

    @Transactional
    @Override
    public String save(String infoId, String name) {
        return groupDao.insert(infoId, name);
    }

    @Override
    public List<Map<String, Object>> list(String infoId) {
        return groupDao.list(infoId);
    }

    @Override
    public String getByWxGroupId(String infoId, String wxGroupId) {
        Map<String, String> idMapping = cache.get(infoId);
        if (null != idMapping) {
            return idMapping.get(wxGroupId);
        }
        List<Map<String, Object>> list = groupDao.listWxGroupId(infoId);
        Map<String, String> organized = organize(list);
        cache.put(infoId, organized);
        return organized.get(wxGroupId);
    }

    @Transactional
    @Override
    public void update(String infoId, String id, String name) {
        groupDao.update(infoId, id, name);
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        groupDao.delete(infoId, id);
        mappingDao.deleteByGroupId(infoId, id);
    }

    private Map<String, String> organize(List<Map<String, Object>> list) {
        Map<String, String> organized = new HashMap<String, String>();
        for (Map<String, Object> map : list) {
            organized.put(MapUtil.get(map, "wx_group_id"), MapUtil.get(map, "id"));
        }
        return organized;
    }

}
