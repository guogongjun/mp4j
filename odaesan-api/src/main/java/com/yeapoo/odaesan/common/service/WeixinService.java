package com.yeapoo.odaesan.common.service;

public interface WeixinService {

    boolean validate(String infoId, String signature, String timestamp, String nonce, String echostr);
}
