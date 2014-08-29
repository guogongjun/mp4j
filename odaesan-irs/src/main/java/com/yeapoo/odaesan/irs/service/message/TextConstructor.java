package com.yeapoo.odaesan.irs.service.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.material.processor.TextHandler;
import com.yeapoo.odaesan.sdk.constants.Constants;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

@Component
public class TextConstructor implements MessageConstructor {

    @Autowired
    private TextHandler handler;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String content = handler.prepareForReply(appInfo, msgId, Constants.MaterialType.TEXT);
        return new TextMessage(input, content);
    }

}
