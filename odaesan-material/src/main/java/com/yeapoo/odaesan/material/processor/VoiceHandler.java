package com.yeapoo.odaesan.material.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;

@Component
public class VoiceHandler extends MaterialHandler {

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        return repository.getVoiceUrl(infoId, msgId);
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        Map<String, Object> voiceInfo = repository.getVoiceInfo(infoId, msgId);
        voiceInfo.put("url", handler.getAbsoluteURL(MapUtil.get(voiceInfo, "url")));
        return voiceInfo;
    }

}
