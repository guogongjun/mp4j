package com.yeapoo.odaesan.irs.dao;

public interface MasssendMessageDao {

    void updateStatistics(String infoId, String wxMsgId, String status, int totalCount, int filterCount, int sentCount, int errorCount);

}