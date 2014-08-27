package com.yeapoo.odaesan.irs.service.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.material.processor.VoiceProcessor;
import com.yeapoo.odaesan.sdk.model.MimeType;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;

@Component
public class VoiceConstructor implements MessageConstructor {

    @Autowired
    private VoiceProcessor processor;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String mediaId = processor.generateMediaId(appInfo, msgId, MimeType.VOICE);
        return new VoiceMessage(input, mediaId);
    }

}
