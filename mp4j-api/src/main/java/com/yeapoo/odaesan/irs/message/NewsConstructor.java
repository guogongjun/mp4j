package com.yeapoo.odaesan.irs.message;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.material.repository.MaterialRepository;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.NewsItem;
import com.yeapoo.odaesan.sdk.model.message.NewsMessage;

@Component
public class NewsConstructor implements MessageConstructor {

    @Value("${app.baseURL}")
    private String baseURL;
    private static final String URL_TEMPLATE = "%s/page/%s/%s";

    @Autowired
    private MaterialRepository repository;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        List<Map<String, Object>> newsList = repository.getNewsForReply(infoId, msgId);

        NewsMessage message = new NewsMessage(input);
        NewsItem item = null;
        for (Map<String, Object> map : newsList) {
            item = new NewsItem(MapUtil.get(map, "title"), MapUtil.get(map, "digest"), MapUtil.get(map, "pic_url"), String.format(URL_TEMPLATE, baseURL, infoId, MapUtil.get(map, "id")));
            message.addAtricle(item);
        }
        return message;
    }

}
