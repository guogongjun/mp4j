package com.yeapoo.odaesan.irs.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.irs.dao.UserDao;
import com.yeapoo.odaesan.irs.dao.UserGroupDao;
import com.yeapoo.odaesan.irs.dao.UserGroupMappingDao;
import com.yeapoo.odaesan.irs.service.FollowerService;
import com.yeapoo.odaesan.irs.service.support.AppInfoProvider;
import com.yeapoo.odaesan.sdk.client.FollowerClient;
import com.yeapoo.odaesan.sdk.client.GroupClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Follower;

@Service
public class FollowerServiceImpl implements FollowerService {
    private static Logger logger = LoggerFactory.getLogger(FollowerServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserGroupDao groupDao;
    @Autowired
    private UserGroupMappingDao mappingDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private FollowerClient followerClient;
    @Autowired
    private GroupClient groupClient;

    private Method getFollowerInfo = ReflectionUtils.findMethod(FollowerClient.class, "getFollowerInfo", new Class<?>[] {Authorization.class, String.class});
    private Method getFollowerGroup = ReflectionUtils.findMethod(GroupClient.class, "getFollowerGroup", new Class<?>[] {Authorization.class, String.class});

    private Map<String, Map<String, String>> cache = new ConcurrentHashMap<String, Map<String,String>>();

    @Override
    @Async
    @Transactional
    public void subscribe(String infoId, String openid) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);

        Object infoResult = adapter.invoke(followerClient, getFollowerInfo, new Object[] {null, openid}, appInfo);
        if (null == infoResult) {
            logger.error("get follower info for openid {} failed", openid);
        }
        Object groupResult = adapter.invoke(groupClient, getFollowerGroup, new Object[] {null, openid}, appInfo);
        if (null == groupResult) {
            logger.error("get follower groupid for openid {} failed", openid);
        }
        userDao.insert(infoId, Follower.class.cast(infoResult));
        mappingDao.insert(infoId, openid, getByWxGroupId(infoId, groupResult.toString()));
        mappingDao.insert(infoId, openid, Constants.UserGroup.ALL_ID);
    }

    @Override
    @Transactional
    public void unsubscribe(String infoId, String openid) {
        userDao.updateUnsubscribeTime(infoId, openid);
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

    private Map<String, String> organize(List<Map<String, Object>> list) {
        Map<String, String> organized = new HashMap<String, String>();
        for (Map<String, Object> map : list) {
            organized.put(MapUtil.get(map, "wx_group_id"), MapUtil.get(map, "id"));
        }
        return organized;
    }
}
