package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.irs.service.MasssendService;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.MassSendJobFinishEventMessage;

@Component
public class MasssendJobFinishResolver extends ResolverAdapter<MassSendJobFinishEventMessage> {

    @Autowired
    private MasssendService masssendService;

    @Override
    public Message resolve(MassSendJobFinishEventMessage input, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String wxMsgId = input.getMessageId();
        String status = input.getStatus();
        int totalCount = input.getTotalCount();
        int filterCount = input.getFilterCount();
        int sentCount = input.getSentCount();
        int errorCount = input.getErrorCount();
        masssendService.updateStatistics(infoId, wxMsgId, status, totalCount, filterCount, sentCount, errorCount);
        return null;
    }

}
