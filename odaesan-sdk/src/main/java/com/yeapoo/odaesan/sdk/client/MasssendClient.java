package com.yeapoo.odaesan.sdk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.ErrorResponse;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendGroupArg;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendOpenidArg;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendResponse;

@Component
public class MasssendClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(MasssendClient.class);

    @Value("${wx.messsent.group.send}")
    private String groupSendURL;
    @Value("${wx.messsent.openid.send}")
    private String openidSendURL;
    @Value("${wx.messsent.delete}")
    private String deleteURL;

    public MasssendResponse masssendByGroup(Authorization authorization, MasssendGroupArg body) {
        HttpEntity<String> request = new HttpEntity<String>(body.toJSON(), headers);
        String response = template.postForObject(groupSendURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, MasssendResponse.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    public MasssendResponse masssendByOpenid(Authorization authorization, MasssendOpenidArg body) {
        HttpEntity<String> request = new HttpEntity<String>(body.toJSON(), headers);
        String response = template.postForObject(openidSendURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, MasssendResponse.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    public boolean deleteMessage(Authorization authorization, long msgId) {
        HttpEntity<String> request = new HttpEntity<String>(String.format("{\"msg_id\":%s}", msgId), headers);
        String response = template.postForObject(deleteURL, request, String.class, authorization.getAccessToken());
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
