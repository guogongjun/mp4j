package com.yeapoo.odaesan.irs.service.message;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.material.repository.MaterialRepository;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.NewsItem;
import com.yeapoo.odaesan.sdk.model.message.NewsMessage;

@Component
public class NewsConstructor implements MessageConstructor {

    @Autowired
    private MaterialRepository repository;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        List<Map<String, Object>> newsList = repository.getNewsForReply(infoId, msgId);

        NewsMessage message = new NewsMessage(input);
        NewsItem item = null;
        for (Map<String, Object> map : newsList) {
            item = new NewsItem(MapUtil.get(map, "title"), MapUtil.get(map, "digest"), MapUtil.get(map, "pic_url"), MapUtil.get(map, "url"));
            message.addAtricle(item);
        }
        return message;
    }

}
