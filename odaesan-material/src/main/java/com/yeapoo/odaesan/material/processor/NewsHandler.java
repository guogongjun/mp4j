package com.yeapoo.odaesan.material.processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.exception.MediaUploadException;
import com.yeapoo.odaesan.sdk.client.MediaClient;
import com.yeapoo.odaesan.sdk.constants.Constants;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Media;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendNews;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendNewsItem;

@Component
public class NewsHandler extends MaterialHandler {
    private static Logger logger = LoggerFactory.getLogger(NewsHandler.class);

    private static Method uploadNews = ReflectionUtils.findMethod(MediaClient.class, "uploadNews", new Class<?>[] {Authorization.class, MasssendNews.class});

    @Override
    protected Media uploadToWeixin(Map<String, Object> appInfo, String msgId, String materialType) {
        String infoId = MapUtil.get(appInfo, "id");
        List<Map<String, Object>> newsList = repository.getNewsForMasssend(infoId, msgId);

        MasssendNews news = new MasssendNews();
        MasssendNewsItem item = null;
        for (Map<String, Object> map : newsList) {
            item = new MasssendNewsItem();
            item.setTitle(MapUtil.get(map, "title"));
            item.setAuthor(MapUtil.get(map, "author"));
            item.setDigest(MapUtil.get(map, "digest"));
            item.setContent(MapUtil.get(map, "content"));
            item.setContentSourceUrl(MapUtil.get(map, "content_source_url"));
            String relativePath = MapUtil.get(map, "url");
            String filePath = handler.getAbsolutePath(relativePath);
            Object result = adapter.invoke(mediaClient, upload, new Object[] {null, filePath, Constants.MaterialType.IMAGE}, appInfo);
            if (null != result) {
                Media media = Media.class.cast(result);
                item.setThumbMediaId(media.getMediaId());
            } else {
                logger.error("failed to upload thumb {} within news {}", filePath, news);
            }

            news.addAtricle(item);
        }

        Object result = adapter.invoke(mediaClient, uploadNews, new Object[] {null, news}, appInfo);
        if (null != result) {
            return Media.class.cast(result);
        } else {
            throw new MediaUploadException(String.format("failed to upload news ", msgId));
        }
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        List<Map<String, Object>> news = repository.getNews(infoId, msgId);
        Map<String, Object> additional = new HashMap<String, Object>();
        additional.put("news", news);
        return additional;
    }

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        // NewsProcessor has implemented uploadToWeixin, so this is method do not need to implement.
        return null;
    }

}
