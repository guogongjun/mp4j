package com.yeapoo.odaesan.material.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;

@Component
public class ImageProcessor extends MaterialProcessor {

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        Map<String, Object> imageInfo = repository.getImage(infoId, msgId);
        return MapUtil.get(imageInfo, "url");
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        Map<String, Object> additional = repository.getImage(infoId, msgId);
        String url = MapUtil.get(additional, "url");
        additional.put("url", handler.getAbsoluteURL(url));
        return additional;
    }

}
