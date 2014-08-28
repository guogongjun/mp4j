package com.yeapoo.odaesan.material.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TextProcessor extends MaterialProcessor {

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
