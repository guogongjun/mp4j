package com.yeapoo.odaesan.irs.message;

import java.util.Map;

import com.yeapoo.odaesan.sdk.model.message.Message;

public interface MessageConstructor {

    Message construct(String msgId, Message input, Map<String, Object> appInfo);

}
