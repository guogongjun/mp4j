package com.yeapoo.odaesan.material.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class VideoProcessor extends MaterialProcessor {

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        return repository.getVideoUrl(infoId, msgId);
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        // TODO Auto-generated method stub
        return null;
    }

}
