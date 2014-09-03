package com.yeapoo.odaesan.api.task;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.service.UserService;
import com.yeapoo.odaesan.common.adapter.FollowerWrapper;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.util.BeanFactoryUtil;
import com.yeapoo.odaesan.sdk.client.FollowerClient;
import com.yeapoo.odaesan.sdk.client.GroupClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Follower;

public class FetchFollowerTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(FetchFollowerTask.class);

    private Map<String, Object> appInfo;
    private List<String> openids;

    private UserService userService;

    private FollowerClient followerClient;
    private GroupClient groupClient;

    private WeixinSDKAdapter adapter;

    private static final int COMMIT_SIZE = 500;

    private Method getFollowerInfo = ReflectionUtils.findMethod(FollowerClient.class, "getFollowerInfo", new Class<?>[] {Authorization.class, String.class});
    private Method getFollowerGroup = ReflectionUtils.findMethod(GroupClient.class, "getFollowerGroup", new Class<?>[] {Authorization.class, String.class});

    public FetchFollowerTask(Map<String, Object> appInfo, List<String> openids) {
        super();
        this.appInfo = appInfo;
        this.openids = openids;

        this.userService = BeanFactoryUtil.getBean(UserService.class);
        this.followerClient = BeanFactoryUtil.getBean(FollowerClient.class);
        this.groupClient = BeanFactoryUtil.getBean(GroupClient.class);
        this.adapter = BeanFactoryUtil.getBean(WeixinSDKAdapter.class);
    }

    @Override
    public void run() {
        String infoId = MapUtil.get(appInfo, "id");

        List<FollowerWrapper> followerList = new ArrayList<FollowerWrapper>();
        List<Object[]> groupMappingList = new ArrayList<Object[]>();
        Object infoResult = null;
        Object groupResult = null;
        int i = 0;
        for (String openid : openids) {
            i++;
            infoResult = adapter.invoke(followerClient, getFollowerInfo, new Object[] {null, openid}, appInfo);
            if (null == infoResult) {
                logger.error("get follower info for openid {} failed", openid);
                continue;
            }
            groupResult = adapter.invoke(groupClient, getFollowerGroup, new Object[] {null, openid}, appInfo);
            if (null == groupResult) {
                logger.error("get follower groupid for openid {} failed", openid);
                continue;
            }

            String groupId = groupResult.toString();
            boolean ungrouped = Constants.UserGroup.UNGROUPED_ID.equals(groupId);
            if (!ungrouped) {
                groupMappingList.add(new Object[] {infoId, openid, groupId});
            }
            followerList.add(new FollowerWrapper(Follower.class.cast(infoResult), ungrouped));
            if (i % COMMIT_SIZE == 0) {
                userService.save(infoId, followerList);
                userService.save(groupMappingList);
                followerList.clear();
                groupMappingList.clear();
            }
        }
        if (followerList.size() > 0) {
            userService.save(infoId, followerList);
            userService.save(groupMappingList);
        }
    }

}
