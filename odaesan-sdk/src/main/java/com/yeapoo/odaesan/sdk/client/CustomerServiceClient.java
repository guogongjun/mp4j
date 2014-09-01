package com.yeapoo.odaesan.sdk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.ErrorResponse;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class CustomerServiceClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(CustomerServiceClient.class);

    @Value("${wx.async.reply}")
    private String asyncReplyURL;

    public ErrorResponse replyMessage(Authorization authorization, Message message) {
        HttpEntity<String> request = new HttpEntity<String>(message.toJSON(), headers);
        String response = template.postForObject(asyncReplyURL, request, String.class, authorization.getAccessToken());
        logger.debug("Weixin Response => {}", response);
        try {
            return mapper.readValue(response, ErrorResponse.class);
        } catch (Exception e) {
            throw new WeixinSDKException(response, e);
        }
    }

}
