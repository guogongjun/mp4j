package com.yeapoo.odaesan.irs.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.service.KeywordService;
import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Component
public class DefaultResolver extends ResolverAdapter<Message> {

    @Autowired
    private KeywordService keywordService;

    @Override
    public Message resolve(Message input, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        return keywordService.getReplyByName(infoId, Constants.Keyword.DEFAULT, input);
    }

}
