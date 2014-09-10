package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface KeywordDao {

    void batchInsert(String groupId, List<Map<String, Object>> keywordList);

    int count(String infoId);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    List<Map<String, Object>> get(String infoId, String id);

    void delete(String id);

    //================== following is for IRS ============================

    Map<String, Object> find(String infoId, String content);

    Map<String, Object> findFuzzy(String infoId, String content);

    Map<String, Object> findByName(String infoId, String ruleName);
}