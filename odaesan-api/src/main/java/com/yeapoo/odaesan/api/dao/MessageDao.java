package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface MessageDao {

    int count(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter);

    List<Map<String, Object>> list(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter, Pagination pagination);

    List<Map<String, Object>> getAdditionalInfo(String infoId, String msgId);
}