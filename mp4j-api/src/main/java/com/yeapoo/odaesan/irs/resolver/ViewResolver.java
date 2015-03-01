package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.ViewEventMessage;

@Component
public class ViewResolver extends ResolverAdapter<ViewEventMessage> {

    @Override
    public Message resolve(ViewEventMessage input, Map<String, Object> params) {
        String url = input.getEventKey();
        return null;
    }

}
