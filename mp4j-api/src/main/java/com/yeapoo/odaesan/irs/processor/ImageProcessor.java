package com.yeapoo.odaesan.irs.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.common.service.MessageService;
import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.irs.resolver.DefaultResolver;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class ImageProcessor implements Processor<ImageMessage> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private DefaultResolver defaultResolver;

    @Override
    public Message process(ImageMessage input, Map<String, Object> params) {
        messageService.save(input, params);
        return defaultResolver.resolve(input, params);
    }

}
