package com.yeapoo.odaesan.common.service;

import java.util.Map;

public interface AppInfoService {

    Map<String, Object> save(String weixinId);

    void updateByWeixinID(String weixinId, String appId, String appSecret);
}
