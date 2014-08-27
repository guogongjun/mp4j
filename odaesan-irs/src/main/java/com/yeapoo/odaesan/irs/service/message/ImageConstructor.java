package com.yeapoo.odaesan.irs.service.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.material.processor.ImageProcessor;
import com.yeapoo.odaesan.sdk.model.MimeType;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class ImageConstructor implements MessageConstructor {

    @Autowired
    private ImageProcessor processor;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String mediaId = processor.generateMediaId(appInfo, msgId, MimeType.IMAGE);
        return new ImageMessage(input, mediaId);
    }

}
