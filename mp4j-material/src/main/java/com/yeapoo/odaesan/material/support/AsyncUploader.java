package com.yeapoo.odaesan.material.support;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.material.processor.ImageHandler;
import com.yeapoo.odaesan.material.processor.MaterialHandler;
import com.yeapoo.odaesan.material.processor.VideoHandler;
import com.yeapoo.odaesan.material.processor.VoiceHandler;
import com.yeapoo.odaesan.material.repository.MaterialRepository;
import com.yeapoo.odaesan.sdk.model.Media;

@Component
public class AsyncUploader {

    @Autowired
    private ImageHandler imageHandler;
    @Autowired
    private VoiceHandler voiceHandler;
    @Autowired
    private VideoHandler videoHandler;

    @Autowired
    protected MaterialRepository repository;

    @Async
    public void uploadImageToWeixin(Map<String, Object> appInfo, String msgId, String materialType) {
        innerUpload(appInfo, msgId, materialType, imageHandler);
    }

    @Async
    public void uploadVoiceToWeixin(Map<String, Object> appInfo, String msgId, String materialType) {
        innerUpload(appInfo, msgId, materialType, voiceHandler);
    }

    @Async
    public void uploadVideoToWeixin(Map<String, Object> appInfo, String msgId, String materialType) {
        innerUpload(appInfo, msgId, materialType, videoHandler);
    }

    private void innerUpload(Map<String, Object> appInfo, String msgId, String materialType, MaterialHandler handler) {
        String infoId = MapUtil.get(appInfo, "id");

        Media media = handler.uploadToWeixin(appInfo, msgId, materialType);

        String mediaId = media.getMediaId();
        int createTime = media.getCreatedAt();

        repository.insertMedia(infoId, materialType, msgId, mediaId, createTime);
    }
}
