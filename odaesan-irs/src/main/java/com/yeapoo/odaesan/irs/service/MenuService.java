package com.yeapoo.odaesan.irs.service;

import com.yeapoo.odaesan.sdk.model.message.Message;

public interface MenuService {

    Message getReplyByKeycode(String infoId, String eventKey, Message input);

}