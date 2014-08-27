package com.yeapoo.odaesan.irs.service;

import com.yeapoo.odaesan.sdk.model.message.Message;

public interface KeywordService {

    Message getReplyByKeyword(String infoId, String content, Message input);

    Message getReplyByName(String infoId, String ruleName, Message input);

}