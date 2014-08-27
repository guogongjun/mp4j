package com.yeapoo.odaesan.api.dao;

import java.util.Map;

public interface KeywordGroupDao {

    String insert(String infoId, String ruleName, String replyId, String replyType);

    Map<String, Object> get(String infoId, String id);

    Map<String, Object> getInfo(String infoId, String ruleName);

    void update(String infoId, String id, String ruleName, String replyId, String replyType);

    void delete(String infoId, String id);

}