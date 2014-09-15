package com.yeapoo.odaesan.common.service;

import java.util.Map;

public interface WeixinService {

    boolean validate(String infoId, String signature, String timestamp, String nonce, String echostr);

    Map<String, Object> getName(String infoId);
}
