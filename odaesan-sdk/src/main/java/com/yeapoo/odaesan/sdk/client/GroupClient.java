package com.yeapoo.odaesan.sdk.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.ErrorResponse;
import com.yeapoo.odaesan.sdk.model.Group;
import com.yeapoo.odaesan.sdk.model.GroupContainer;

@Component
public class GroupClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(GroupClient.class);

    @Value("${wx.group.create}")
    private String groupCreateURL;
    @Value("${wx.group.get}")
    private String groupGetURL;
    @Value("${wx.group.update}")
    private String groupRenameURL;
    @Value("${wx.group.mvuser}")
    private String moveUserURL;
    @Value("${wx.group.getid}")
    private String getGroupIdURL;

    @SuppressWarnings("unchecked")
    public Group createGroup(Authorization authorization, String groupName) {
        Group group = new Group(groupName);
        HttpEntity<String> request = new HttpEntity<String>(group.toCreateString(), headers);
        String response = template.postForObject(groupCreateURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            Map<String, Object> groupContainer = mapper.readValue(response, Map.class);
            Map<String, Object> groupMap = Map.class.cast(groupContainer.get("group"));
            return mapper.convertValue(groupMap, Group.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    public GroupContainer getGroup(Authorization authorization) {
        String response = template.getForObject(groupGetURL, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, GroupContainer.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getFollowerGroup(Authorization authorization, String openid) {
        HttpEntity<String> request = new HttpEntity<String>(String.format("{\"openid\":\"%s\"}", openid), headers);
        String response = template.postForObject(getGroupIdURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            Map<String, Object> groupIdMap = mapper.readValue(response, Map.class);
            Integer groupId = MapUtil.get(groupIdMap, "groupid", Number.class).intValue();

            Assert.notNull(groupId);
            return groupId;
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    /**
     * @param group  id and name properties are required in group model
     */
    public boolean renameGroup(Authorization authorization, Group group) {
        HttpEntity<String> request = new HttpEntity<String>(group.toUpdateString(), headers);
        String response = template.postForObject(groupRenameURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            ErrorResponse error = mapper.readValue(response, ErrorResponse.class);
            int errcode =  error.getErrorCode();
            return errcode == 0;
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    public boolean moveUserGroup(Authorization authorization, String openid, int destGroupId) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("openid", openid);
        json.put("to_groupid", destGroupId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(json, headers);
        String response = template.postForObject(moveUserURL, request, String.class, authorization.getAccessToken());

        logger.debug(String.format("Weixin Response => %s", response));
        try {
            ErrorResponse error = mapper.readValue(response, ErrorResponse.class);
            int errcode =  error.getErrorCode();
            return errcode == 0;
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

}
