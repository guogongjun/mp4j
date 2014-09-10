package com.yeapoo.odaesan.irs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeapoo.odaesan.irs.dao.AppInfoDao;
import com.yeapoo.odaesan.irs.service.WeixinService;
import com.yeapoo.odaesan.sdk.util.TokenValidator;

@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private AppInfoDao appInfoDao;

    @Override
    public boolean validate(String infoId, String signature, String timestamp, String nonce, String echostr) {
        String token = appInfoDao.getToken(infoId);
        return TokenValidator.validate(signature, timestamp, nonce, token);
    }

}
