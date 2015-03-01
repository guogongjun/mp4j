package com.yeapoo.odaesan.irs.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.material.processor.ImageHandler;
import com.yeapoo.odaesan.sdk.constants.Constants;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class ImageConstructor implements MessageConstructor {

    @Autowired
    private ImageHandler handler;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String mediaId = handler.prepareForReply(appInfo, msgId, Constants.MaterialType.IMAGE);
        return new ImageMessage(input, mediaId);
    }

}
