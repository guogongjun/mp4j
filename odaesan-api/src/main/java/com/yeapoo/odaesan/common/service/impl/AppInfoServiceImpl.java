package com.yeapoo.odaesan.common.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeapoo.odaesan.common.dao.AppInfoDao;
import com.yeapoo.odaesan.common.service.AppInfoService;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoDao appInfoDao;

    @Transactional
    @Override
    public Map<String, Object> save(String weixinId) {
        return appInfoDao.insert(weixinId);
    }

    @Override
    public void updateByWeixinID(String weixinId, String appId, String appSecret) {
        appInfoDao.updateByWeixinID(weixinId, appId, appSecret);
    }

}
