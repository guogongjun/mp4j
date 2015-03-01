package com.yeapoo.odaesan.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeapoo.odaesan.common.dao.QRCodeStatsDao;
import com.yeapoo.odaesan.common.service.QRCodeService;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private QRCodeStatsDao qrcodeStatsDao;

    @Override
    public void saveScanInfo(String infoId, String sceneId, String openid, String ticket, boolean isNewFollower, Date createTime) {
        qrcodeStatsDao.insert(infoId, sceneId, openid, ticket, isNewFollower, createTime);
    }

}
