package com.yeapoo.odaesan.sdk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.ErrorResponse;
import com.yeapoo.odaesan.sdk.model.Follower;
import com.yeapoo.odaesan.sdk.model.FollowerContainer;

@Component
public class FollowerClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(FollowerClient.class);

    @Value("${wx.follower.list}")
    private String followerListURL;
    @Value("${wx.follower.info}")
    private String followerInfoURL;
    @Value("${wx.follower.update.remark}")
    private String updateRemarkURL;

    /**
     * 获取关注者列表，每次最多10,000个。
     */
    public FollowerContainer getFollowerList(Authorization authorization, String nextOpenid) {
        if (nextOpenid == null) {
            nextOpenid = "";
        }
        String response = template.getForObject(followerListURL, String.class, authorization.getAccessToken(), nextOpenid);
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, FollowerContainer.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    /**
     * 获取用户基本信息
     */
    public Follower getFollowerInfo(Authorization authorization, String openid) {
        String response = template.getForObject(followerInfoURL, String.class, authorization.getAccessToken(), openid);
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, Follower.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    /**
     * 设置用户备注名
     */
    public ErrorResponse updateRemark(Authorization authorization, String openid, String remark) {
        String body = String.format("{\"openid\":\"%s\",\"remark\":\"%s\"}", openid, remark);
        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        String response = template.postForObject(updateRemarkURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, ErrorResponse.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }
}
