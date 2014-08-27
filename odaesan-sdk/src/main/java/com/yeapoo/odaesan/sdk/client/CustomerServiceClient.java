package com.yeapoo.odaesan.sdk.client;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class CustomerServiceClient implements BaseClient {
    private static Logger logger = LoggerFactory.getLogger(CustomerServiceClient.class);

    @Value("${wx.async.reply}")
    private String asyncReplyURL;

    @Autowired
    private RestTemplate template;
    @Autowired
    private ObjectMapper mapper;

    private HttpHeaders headers;
    public CustomerServiceClient() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public String replyMessage(Authorization authorization, Message message) {
        HttpEntity<String> request = new HttpEntity<String>(message.toJSON(), headers);
        String response = template.postForObject(asyncReplyURL, request, String.class, authorization.getAccessToken());
        logger.debug("Weixin Response => {}", response);
        return response;
    }

}
