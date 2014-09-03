package com.yeapoo.odaesan.api.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.odaesan.api.dao.UserDao;
import com.yeapoo.odaesan.api.dao.UserGroupDao;
import com.yeapoo.odaesan.api.dao.UserGroupMappingDao;
import com.yeapoo.odaesan.api.service.UserService;
import com.yeapoo.odaesan.api.service.support.AppInfoProvider;
import com.yeapoo.odaesan.api.task.FetchFollowerTask;
import com.yeapoo.odaesan.common.adapter.FollowerWrapper;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.client.FollowerClient;
import com.yeapoo.odaesan.sdk.client.GroupClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.FollowerContainer;

@Service
public class UserServiceImpl implements UserService {

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
    @Autowired
    private TaskExecutor executor;

    private Method getFollowerList = ReflectionUtils.findMethod(FollowerClient.class, "getFollowerList", new Class<?>[] {Authorization.class, String.class});

    @Override
    public boolean hasFetched(String infoId) {
        int count = groupDao.count(infoId);
        return count > 0;
    }

    @Override
    public void fetchInfo(String infoId, String nextOpenid) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);

        int count = 0;
        FollowerContainer container = null;
        Object result = null;
        do {
            result = adapter.invoke(followerClient, getFollowerList, new Object[] {null, nextOpenid}, appInfo);
            if (null != result) {
                container = FollowerContainer.class.cast(result);
                nextOpenid = container.getNextOpenid();
                count = container.getCount();
                if (count > 0) {
                    List<String> openids = container.getOpenids();
                    executor.execute(new FetchFollowerTask(appInfo, openids));
                }
            }
        } while (count > 0);
    }

    @Transactional
    @Override
    public void save(String infoId, List<FollowerWrapper> followerList) {
        userDao.batchInsert(infoId, followerList);
    }

    @Transactional
    @Override
    public void save(List<Object[]> groupMappingList) {
        mappingDao.batchInsert(groupMappingList);
    }

    @Transactional
    @Override
    public void reset(String infoId) {
        mappingDao.truncate(infoId);
        userDao.truncate(infoId);
        groupDao.truncate(infoId);
    }

    @Override
    public List<Map<String, Object>> listByGroup(String infoId, String groupId, Pagination pagination) {
        if (Constants.UserGroup.ALL_ID.equals(groupId)) {
            pagination.setCount(userDao.count(infoId));
            return userDao.findAll(infoId, pagination);
        } else  {
            pagination.setCount(userDao.count(infoId, groupId));
            return userDao.findAll(infoId, groupId, pagination);
        }
    }

    @Override
    public Map<String, Object> get(String infoId, String openid) {
        return userDao.get(infoId, openid);
    }

    @Override
    public List<Map<String, Object>> listGroups(String infoId, String openid) {
        List<Map<String, Object>> mapping = mappingDao.findByOpenid(infoId, openid);
        if (mapping.isEmpty()) {
            Map<String, Object> group = new HashMap<String, Object>();
            group.put("group_id", Constants.UserGroup.UNGROUPED_ID);
            mapping.add(group);
        }
        return mapping;
    }

    @Transactional
    @Override
    public void fakeMoveUserToGroup(String infoId, String openid, String current, String target) {
        mappingDao.delete(infoId, openid, current);
        mappingDao.insert(infoId, openid, target);
    }

    @Transactional
    @Override
    public void fakeCopyUserToGroup(String infoId, String openid, String current, String target) {
        mappingDao.insert(infoId, openid, target);
    }

    @Transactional
    @Override
    public void fakeBatchMoveUserToGroup(String infoId, List<String> openidList, String current, String target) {
        mappingDao.batchDelete(infoId, openidList, current);
        mappingDao.batchInsert(infoId, openidList, target);
    }

    @Transactional
    @Override
    public void fakeBatchCopyUserToGroup(String infoId, List<String> openidList, String current, String target) {
        mappingDao.batchInsert(infoId, openidList, target);
    }

    @Transactional
    @Override
    public void fakeRemoveUserFromGroup(String infoId, String openid, String groupId) {
        mappingDao.delete(infoId, openid, groupId);
        List<Map<String, Object>> list = mappingDao.findByOpenid(infoId, openid);
        if (list.isEmpty()) {
            userDao.updateUngrouped(infoId, openid, true);
        }
    }

    @Transactional
    @Override
    public void fakeBatchRemoveUserFromGroup(String infoId, List<String> openidList, String groupId) {
        mappingDao.batchDelete(infoId, openidList, groupId);
        Iterator<String> iterator = openidList.iterator();
        while (iterator.hasNext()) {
            String openid = iterator.next();
            List<Map<String, Object>> list = mappingDao.findByOpenid(infoId, openid);
            if (!list.isEmpty()) {
                iterator.remove();
            }
        }
        userDao.batchUpdateUngrouped(infoId, openidList, true);
    }

    @Transactional
    @Override
    public void fakeAddUserToGroup(String infoId, String openid, String groupId) {
        mappingDao.insert(infoId, openid, groupId);
        if (!Constants.UserGroup.UNGROUPED_ID.equals(groupId)) {
            userDao.updateUngrouped(infoId, openid, false);
        }
    }

    @Transactional
    @Override
    public void fakeBatchAddUserToGroup(String infoId, List<String> openidList, String groupId) {
        mappingDao.batchInsert(infoId, openidList, groupId);
        if (!Constants.UserGroup.UNGROUPED_ID.equals(groupId)) {
            userDao.batchUpdateUngrouped(infoId, openidList, false);
        }
    }

}
