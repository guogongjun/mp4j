package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.ScanEventMessage;

@Component
public class ScanResolver extends ResolverAdapter<ScanEventMessage> {

    private static final int BEGIN_INDEX = "qrscene_".length();

    @Override
    public Message resolve(ScanEventMessage input, Map<String, Object> params) {
        String event = input.getEvent();
        String sceneId = input.getEventKey();
        if ("subscribe".equals(event)) {
            sceneId = sceneId.substring(BEGIN_INDEX);
        }
        String ticket = input.getTicket();
        return null;
    }
}
