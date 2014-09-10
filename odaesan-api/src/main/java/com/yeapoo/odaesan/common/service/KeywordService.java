package com.yeapoo.odaesan.common.service;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.message.Message;

public interface KeywordService {

    String save(String infoId, String ruleName, List<Map<String, Object>> keywordList, String replyId, String replyType);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    Map<String, Object> get(String infoId, String id);

    Map<String, Object> getSubscribeInfo(String infoId);

    Map<String, Object> getDefaultInfo(String infoId);

    void update(String infoId, String id, String ruleName, List<Map<String, Object>> keywordList, String replyId, String replyType);

    void delete(String infoId, String id);

    //================== following is for IRS ============================

    Message getReplyByKeyword(String infoId, String content, Message input);

    Message getReplyByName(String infoId, String ruleName, Message input);
}
