package com.yeapoo.odaesan.irs.service;

public interface WeixinService {

    boolean validate(String infoId, String signature, String timestamp, String nonce, String echostr);
}
