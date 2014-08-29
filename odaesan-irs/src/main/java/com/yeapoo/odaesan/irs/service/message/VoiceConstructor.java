package com.yeapoo.odaesan.irs.service.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.material.processor.VoiceHandler;
import com.yeapoo.odaesan.sdk.constants.Constants;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;

@Component
public class VoiceConstructor implements MessageConstructor {

    @Autowired
    private VoiceHandler handler;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String mediaId = handler.prepareForReply(appInfo, msgId, Constants.MaterialType.VOICE);
        return new VoiceMessage(input, mediaId);
    }

}
