package com.yeapoo.odaesan.irs.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.irs.resolver.DefaultResolver;
import com.yeapoo.odaesan.irs.service.KeywordService;
import com.yeapoo.odaesan.irs.service.MessageService;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;

@Component
public class VoiceProcessor implements Processor<VoiceMessage> {

    @Autowired
    private MessageService messageService;
    @Autowired
    private KeywordService keywordService;

    @Autowired
    private DefaultResolver defaultResolver;

    @Override
    public Message process(VoiceMessage input, Map<String, Object> params) {
        messageService.save(input, params);
        String recognition = input.getRecognition();
        if (StringUtils.hasText(recognition)) {
            String infoId = MapUtil.get(params, "info_id");
            return keywordService.getReplyByKeyword(infoId, recognition, input);
        }
        return defaultResolver.resolve(input, params);
    }

}
