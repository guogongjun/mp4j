package com.yeapoo.odaesan.material.processor;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.exception.MediaUploadException;
import com.yeapoo.odaesan.sdk.client.MediaClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Media;
import com.yeapoo.odaesan.sdk.util.MediaUtil;

@Component
public class VideoHandler extends MaterialHandler {

    private static Method uploadVideo = ReflectionUtils.findMethod(MediaClient.class, "uploadVideo", new Class<?>[] {Authorization.class, String.class, String.class, String.class});

    /**
     * 视频素材在群发时需要额外的上传操作
     */
    @Override
    public String prepareForMasssend(Map<String, Object> appInfo, String msgId, String materialType) {
        String infoId = MapUtil.get(appInfo, "id");
        Map<String, Object> mediaInfo = repository.getMedia(infoId, materialType, msgId);
        if (null != mediaInfo) {
            int createTime = MapUtil.get(mediaInfo, "create_time", Number.class).intValue();
            if (!MediaUtil.hasExpired(createTime)) {
                return MapUtil.get(mediaInfo, "media_id");
            }
        }

        Map<String, Object> videoInfo = repository.getVideoAllInfo(infoId, msgId);
        String title = MapUtil.get(videoInfo, "title");
        String description = MapUtil.get(videoInfo, "description");

        String originalMediaId = prepareForReply(appInfo, msgId, "video");

        Object result = adapter.invoke(mediaClient, uploadVideo, new Object[] {null, originalMediaId, title, description}, appInfo);
        if (null != result) {
            Media media = Media.class.cast(result);
            String mediaId = media.getMediaId();
            int createTime = media.getCreatedAt();
            repository.insertMedia(infoId, materialType, msgId, mediaId, createTime);
            return mediaId;
        } else {
            throw new MediaUploadException(String.format("failed to upload news ", msgId));
        }
    }

    @Override
    protected String getFileRelativePath(String infoId, String msgId) {
        return repository.getVideoUrl(infoId, msgId);
    }

    @Override
    public Map<String, Object> enrichDisplayInfo(String infoId, String msgId) {
        Map<String, Object> videoInfo = repository.getVideoAllInfo(infoId, msgId);
        videoInfo.put("url", handler.getAbsoluteURL(MapUtil.get(videoInfo, "url")));
        return videoInfo;
    }

}
