package com.yeapoo.odaesan.material.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;

@Component
public class VideoProcessor extends MaterialProcessor {

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        return repository.getVideoUrl(infoId, msgId);//TODO
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        Map<String, Object> videoInfo = repository.getVideoAllInfo(infoId, msgId);
        videoInfo.put("url", handler.getAbsoluteURL(MapUtil.get(videoInfo, "url")));
        return videoInfo;
    }

}
