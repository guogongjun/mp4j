package com.yeapoo.odaesan.irs.service.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.material.processor.VideoProcessor;
import com.yeapoo.odaesan.material.repository.MaterialRepository;
import com.yeapoo.odaesan.sdk.model.MimeType;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;

@Component
public class VideoConstructor implements MessageConstructor {

    @Autowired
    private VideoProcessor processor;

    @Autowired
    private MaterialRepository repository;

    @Override
    public Message construct(String msgId, Message input, Map<String, Object> appInfo) {
        String infoId = MapUtil.get(appInfo, "id");
        Map<String, Object> videoInfo = repository.getVideo(infoId, msgId);
        String mediaId = processor.generateMediaId(appInfo, msgId, MimeType.IMAGE);

        VideoMessage message = new VideoMessage(input);
        message.setMediaId(mediaId);
        message.setTitle(MapUtil.get(videoInfo, "title"));
        message.setDescription(MapUtil.get(videoInfo, "description"));
        return message;
    }

}
