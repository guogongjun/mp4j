package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

public interface MessageDao {

    int count(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter);

    List<Map<String, Object>> list(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter, Pagination pagination);

    List<Map<String, Object>> getAdditionalInfo(String infoId, String msgId);

    //================== following is for IRS ============================

    String insert(String infoId, Message msg);

    String insert(String infoId, TextMessage msg);

    void update(String infoId, String id, boolean ivrmsg);

    void insertAdditionalInfo(List<Object[]> batchArgs);
}