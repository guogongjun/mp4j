package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.service.UserService;
import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.EventMessage;

@Component
public class UnsubscribeResolver extends ResolverAdapter<EventMessage> {

    @Autowired
    private UserService userService;

    @Override
    public Message resolve(EventMessage input, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        userService.unsubscribe(infoId, input.getFromUserName());
        return null;
    }

}
