package com.yeapoo.odaesan.irs.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.irs.resolver.DefaultResolver;
import com.yeapoo.odaesan.irs.service.MessageService;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;

@Component
public class VideoProcessor implements Processor<VideoMessage>{

    @Autowired
    private MessageService messageService;

    @Autowired
    private DefaultResolver defaultResolver;

    @Override
    public Message process(VideoMessage input, Map<String, Object> params) {
        messageService.save(input, params);
        return defaultResolver.resolve(input, params);
    }

}
