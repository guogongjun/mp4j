package com.yeapoo.odaesan.irs.dao;

import java.util.Map;

public interface KeywordDao {

    Map<String, Object> find(String infoId, String content);

    Map<String, Object> findFuzzy(String infoId, String content);

    Map<String, Object> findByName(String infoId, String ruleName);

}