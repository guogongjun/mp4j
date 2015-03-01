package com.yeapoo.odaesan.irs.message;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class MusicConstructor implements MessageConstructor {
    private static Logger logger = LoggerFactory.getLogger(MusicConstructor.class);

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> params) {
        logger.warn("unable to construct music message");
        return null;
    }

}
