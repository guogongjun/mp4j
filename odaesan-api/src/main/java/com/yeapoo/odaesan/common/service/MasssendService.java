package com.yeapoo.odaesan.common.service;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface MasssendService {

    void submitMasssendTask(String infoId, String groupId, String gender, String msgType, String msgId);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    Map<String, Object> getStatistics(String infoId, String id);

    //================== following is for IRS ============================

    void updateStatistics(String infoId, String wxMsgId, String status, int totalCount, int filterCount, int sentCount, int errorCount);
}
