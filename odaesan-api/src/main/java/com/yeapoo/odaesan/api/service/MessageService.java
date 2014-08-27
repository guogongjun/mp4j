package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface MessageService {

    List<Map<String, Object>> list(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter, Pagination pagination);

}
