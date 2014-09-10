package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.LocationEventMessage;

@Component
public class LocationEventResolver extends ResolverAdapter<LocationEventMessage> {

    @Override
    public Message resolve(LocationEventMessage input, Map<String, Object> params) {
        double latitude = input.getLatitude();
        double longitude = input.getLongitude();
        double precision = input.getPrecision();
        return null;
    }

}
