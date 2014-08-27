package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface KeywordService {

    String save(String infoId, String ruleName, List<Map<String, Object>> keywordList, String replyId, String replyType);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    Map<String, Object> get(String infoId, String id);

    Map<String, Object> getSubscribeInfo(String infoId);

    Map<String, Object> getDefaultInfo(String infoId);

    void update(String infoId, String id, String ruleName, List<Map<String, Object>> keywordList, String replyId, String replyType);

    void delete(String infoId, String id);

}
