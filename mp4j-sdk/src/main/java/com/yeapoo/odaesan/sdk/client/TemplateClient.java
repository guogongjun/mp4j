package com.yeapoo.odaesan.sdk.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.TemplateMessage;

@Component
public class TemplateClient extends BaseClient {
//    private static Logger logger = LoggerFactory.getLogger(TemplateClient.class);

    @Value("${wx.template.send}")
    private String templateSendURL;

    @SuppressWarnings("unchecked")
    public String send(Authorization authorization, TemplateMessage message) {
        HttpEntity<TemplateMessage> request = new HttpEntity<TemplateMessage>(message, headers);
        String response = template.postForObject(templateSendURL, request, String.class, authorization.getAccessToken());
//        logger.debug(String.format("Weixin Response => %s", response));
        try {
            Map<String, Object> data = mapper.readValue(response, Map.class);
            return MapUtil.get(data, "msgid").toString();
        } catch (Exception e) {
            throw new WeixinSDKException(response, e);
        }
    }
}
