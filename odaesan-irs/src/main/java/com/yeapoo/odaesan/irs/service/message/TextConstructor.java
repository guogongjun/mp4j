package com.yeapoo.odaesan.irs.service.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.material.repository.MaterialRepository;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

@Component
public class TextConstructor implements MessageConstructor {

    @Autowired
    private MaterialRepository repository;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        Map<String, Object> map = repository.getText(infoId, msgId);
        return new TextMessage(input, MapUtil.get(map, "content"));
    }

}
