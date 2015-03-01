package com.yeapoo.odaesan.common.adapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.sdk.client.AuthClient;
import com.yeapoo.odaesan.sdk.model.Authorization;

@Component
public class Authorizer {
    private static Logger logger = LoggerFactory.getLogger(Authorizer.class);

    @Autowired
    private AuthClient authClient;

    private Map<String, Authorization> cache = new ConcurrentHashMap<String, Authorization>();

    public Authorization ensure(Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        Authorization auth = cache.get(infoId);
        if (null == auth || auth.isExpired()) {
            auth = authorize(appInfo);
        }
        return auth;
    }

    public void refresh(Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        cache.remove(infoId);
    }

    public synchronized Authorization authorize(Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        Authorization auth = cache.get(infoId);
        if (null != auth) {
            logger.info(String.format("access token [%s], request at [%s], expected expires in [%s]", auth.getAccessToken(), auth.getLastRequestTime(), auth.getExpiresIn()));
            if (!auth.isExpired()) {
                return auth;
            }
        }

        String appId = MapUtil.get(appInfo, "app_id");
        String appSecret = MapUtil.get(appInfo, "app_secret");
        auth = authClient.requestAuthorization(appId, appSecret);
        cache.put(infoId, auth);
        return auth;
    }
}
