package com.yeapoo.odaesan.irs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeapoo.odaesan.irs.dao.MasssendMessageDao;
import com.yeapoo.odaesan.irs.service.MasssendService;

@Service
public class MasssendServiceImpl implements MasssendService {

    @Autowired
    private MasssendMessageDao masssendDao;

    @Override
    @Transactional
    public void updateStatistics(String infoId, String wxMsgId, String status, int totalCount, int filterCount, int sentCount, int errorCount) {
        masssendDao.updateStatistics(infoId, wxMsgId, status, totalCount, filterCount, sentCount, errorCount);
    }
}
