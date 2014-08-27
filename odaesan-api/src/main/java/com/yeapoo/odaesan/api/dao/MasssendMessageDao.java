package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendResponse;

public interface MasssendMessageDao {

    void insert(String infoId, String msgType, String msgId, MasssendResponse response);

    int count(String infoId);

    List<Map<String, Object>> findAll(String infoId, Pagination pagination);

    Map<String, Object> getStatistics(String infoId, String id);

}