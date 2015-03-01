package com.yeapoo.odaesan.irs.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.service.KeywordService;
import com.yeapoo.odaesan.common.service.MessageService;
import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.irs.resolver.DefaultResolver;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

@Component
public class TextProcessor implements Processor<TextMessage> {

    @Autowired
    private MessageService messageService;
    @Autowired
    private KeywordService keywordService;

    @Autowired
    private DefaultResolver defaultResolver;

    @Override
    public Message process(TextMessage input, Map<String, Object> params) {
        String id = messageService.save(input, params);
        String infoId = MapUtil.get(params, "info_id");
        String content = input.getContent();
        Message message = keywordService.getReplyByKeyword(infoId, content, input);
        if (null != message) {
            messageService.updateKeywordFlag(infoId, id, true);
        } else {
            return defaultResolver.resolve(input, params);
        }
        return message;
    }
}
