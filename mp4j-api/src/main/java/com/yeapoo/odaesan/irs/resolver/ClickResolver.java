package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.service.MenuService;
import com.yeapoo.odaesan.common.service.MessageService;
import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.ClickEventMessage;

@Component
public class ClickResolver extends ResolverAdapter<ClickEventMessage> {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MenuService menuService;

    @Override
    public Message resolve(ClickEventMessage input, Map<String, Object> params) {
        messageService.save(input, params);
        String infoId = MapUtil.get(params, "info_id");
        String eventKey = input.getEventKey();
        return menuService.getReplyByKeycode(infoId, eventKey, input);
    }

}
