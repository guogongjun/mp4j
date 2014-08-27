package com.yeapoo.odaesan.sdk.client;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;

@Component
public class AuthClient implements BaseClient {
    private static Logger logger = LoggerFactory.getLogger(AuthClient.class);

    @Value("${wx.access.token}")
    private String accessTokenURL;

    @Autowired
    private RestTemplate template;
    @Autowired
    private ObjectMapper mapper;

    /**
     * 获取access token
     * 
     * @return Authorization
     * @throws IOException 
     */
    public Authorization requestAuthorization(String appId, String appSecret) {
        String response = template.getForObject(accessTokenURL, String.class, appId, appSecret);
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, Authorization.class);
        } catch (IOException e) {
            throw new WeixinSDKException(e);
        }
    }
}
