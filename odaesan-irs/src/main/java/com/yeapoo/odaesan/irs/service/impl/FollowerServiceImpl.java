package com.yeapoo.odaesan.irs.service.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.irs.dao.UserDao;
import com.yeapoo.odaesan.irs.dao.UserGroupMappingDao;
import com.yeapoo.odaesan.irs.service.FollowerService;
import com.yeapoo.odaesan.irs.service.support.AppInfoProvider;
import com.yeapoo.odaesan.sdk.client.FollowerClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Follower;

@Service
public class FollowerServiceImpl implements FollowerService {
    private static Logger logger = LoggerFactory.getLogger(FollowerServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserGroupMappingDao mappingDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private FollowerClient followerClient;

    private Method getFollowerInfo = ReflectionUtils.findMethod(FollowerClient.class, "getFollowerInfo", new Class<?>[] {Authorization.class, String.class});

    @Override
    @Async
    @Transactional
    public void subscribe(String infoId, String openid) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);

        Object infoResult = adapter.invoke(followerClient, getFollowerInfo, new Object[] {null, openid}, appInfo);
        if (null == infoResult) {
            logger.error("get follower info for openid {} failed", openid);
        }
        userDao.insert(infoId, Follower.class.cast(infoResult));
        mappingDao.insert(infoId, openid, Constants.UserGroup.UNGROUPED_ID);
        mappingDao.insert(infoId, openid, Constants.UserGroup.ALL_ID);
    }

    @Override
    @Transactional
    public void unsubscribe(String infoId, String openid) {
        userDao.updateUnsubscribeTime(infoId, openid);
        mappingDao.removeByOpenid(infoId, openid);
    }

}
