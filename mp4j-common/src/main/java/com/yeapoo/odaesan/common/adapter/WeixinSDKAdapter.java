package com.yeapoo.odaesan.common.adapter;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.sdk.client.BaseClient;
import com.yeapoo.odaesan.sdk.model.Authorization;

@Component
public class WeixinSDKAdapter {
    private static Logger logger = LoggerFactory.getLogger(WeixinSDKAdapter.class);

    @Value("${wx.max.retries}")
    private int maxRetries;

    @Autowired
    private Authorizer authorizer;

    public Object invoke(BaseClient client, Method method, Object[] args, Map<String, Object> appInfo) {
        Authorization auth = authorizer.ensure(appInfo);

        Object result = null;
        int retries = 0;
        while (retries < maxRetries) {
            retries++;
            try {
                args[0] = auth;
                result = method.invoke(client, args);
                break;
            } catch (Exception e) {
                auth = authorizer.authorize(appInfo);
                logger.info("Retry {} time to invoke method[{}] on client[{}]", retries, method.getName(), client.getClass().getName());
                logger.error(e.getMessage(), e);
            }
        }

        return result;
    }
}
