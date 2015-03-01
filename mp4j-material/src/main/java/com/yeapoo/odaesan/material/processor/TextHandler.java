package com.yeapoo.odaesan.material.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;

@Component
public class TextHandler extends MaterialHandler {

    @Override
    public String prepareForReply(Map<String, Object> appInfo, String msgId, String materialType) {
        String infoId = MapUtil.get(appInfo, "id");
        return repository.getTextContent(infoId, msgId);
    }

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        // TextProcessor do not need to implement this method
        return null;
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        return repository.getText(infoId, msgId);
    }

}
