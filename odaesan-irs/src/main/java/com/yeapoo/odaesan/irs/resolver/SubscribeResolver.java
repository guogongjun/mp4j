package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.irs.service.FollowerService;
import com.yeapoo.odaesan.irs.service.KeywordService;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.EventMessage;
import com.yeapoo.odaesan.sdk.model.message.event.ScanEventMessage;

@Component
public class SubscribeResolver extends ResolverAdapter<EventMessage> {

    @Autowired
    private FollowerService followerService;
    @Autowired
    private KeywordService keywordService;

    @Override
    public Message resolve(EventMessage input, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        followerService.subscribe(infoId, input.getFromUserName());
        if (input instanceof ScanEventMessage) {
            return null;
        } else {
            return keywordService.getReplyByName(infoId, Constants.Keyword.SUBSCRIBE, input);
        }
    }

}
