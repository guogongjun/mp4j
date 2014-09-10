package com.yeapoo.odaesan.irs.processor;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.framework.resolver.Resolver;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.EventMessage;

@Component
public class EventProcessor implements Processor<EventMessage> {

    @SuppressWarnings("rawtypes")
    @Resource(name="eventResolvers")
    private Map<String, Resolver> eventResolvers;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Message process(EventMessage input, Map<String, Object> params) {
        String event = input.getEvent();
        Resolver resolver = eventResolvers.get(event);
        Assert.notNull(resolver);
        return resolver.resolve(input, params);
    }

}
