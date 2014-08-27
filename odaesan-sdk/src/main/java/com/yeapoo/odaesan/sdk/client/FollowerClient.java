package com.yeapoo.odaesan.sdk.client;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Follower;
import com.yeapoo.odaesan.sdk.model.FollowerContainer;

@Component
public class FollowerClient implements BaseClient {
    private static Logger logger = LoggerFactory.getLogger(FollowerClient.class);

    @Value("${wx.follower.list}")
    private String followerListURL;
    @Value("${wx.follower.info}")
    private String followerInfoURL;

    @Autowired
    private RestTemplate template;
    @Autowired
    private ObjectMapper mapper;

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

}
