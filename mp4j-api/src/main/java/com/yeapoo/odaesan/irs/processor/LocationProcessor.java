package com.yeapoo.odaesan.irs.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.common.service.MessageService;
import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.irs.resolver.DefaultResolver;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class LocationProcessor implements Processor<LocationMessage> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private DefaultResolver defaultResolver;

    @Override
    public Message process(LocationMessage input, Map<String, Object> params) {
        messageService.save(input, params);
        return defaultResolver.resolve(input, params);
    }

}
