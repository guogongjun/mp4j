package com.yeapoo.odaesan.irs.service;


public interface MasssendService {

    void updateStatistics(String infoId, String wxMsgId, String status, int totalCount, int filterCount, int sentCount, int errorCount);

}