package com.yeapoo.odaesan.material.processor;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.exception.MediaUploadException;
import com.yeapoo.odaesan.material.repository.MaterialRepository;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;
import com.yeapoo.odaesan.sdk.client.MediaClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Media;
import com.yeapoo.odaesan.sdk.model.MimeType;
import com.yeapoo.odaesan.sdk.util.MediaExpireChecker;

public abstract class MaterialProcessor implements BeanFactoryAware, InitializingBean {

    private BeanFactory factory;

    protected MaterialRepository repository;
    protected WeixinSDKAdapter adapter;
    protected MediaClient mediaClient;
    protected StaticResourceHandler handler;

    protected Method upload = ReflectionUtils.findMethod(MediaClient.class, "upload", new Class<?>[] { Authorization.class, String.class, MimeType.class });

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        repository = factory.getBean(MaterialRepository.class);
        adapter = factory.getBean(WeixinSDKAdapter.class);
        mediaClient = factory.getBean(MediaClient.class);
        handler = factory.getBean(StaticResourceHandler.class);
    }

    public String generateMediaId(Map<String, Object> appInfo, String msgId, MimeType mimeType) {
        String infoId = MapUtil.get(appInfo, "id");
        Map<String, Object> mediaInfo = repository.getMedia(infoId, mimeType.toString(), msgId);
        if (null != mediaInfo) {
            int createTime = MapUtil.get(mediaInfo, "create_time", Number.class).intValue();
            if (MediaExpireChecker.check(createTime)) {
                return MapUtil.get(mediaInfo, "media_id");
            }
        }

        String relativePath = getFileRelativePath(infoId, msgId);
        String filePath = handler.getAbsolutePath(relativePath);

        Object result = adapter.invoke(mediaClient, upload, new Object[] {null, filePath, mimeType}, appInfo);
        if (null != result) {
            Media media = Media.class.cast(result);
            String mediaId = media.getMediaId();
            int createTime = media.getCreatedAt();
            repository.insertMedia(infoId, mimeType.toString(), msgId, mediaId, createTime);
            return mediaId;
        } else {
            throw new MediaUploadException(String.format("failed to upload file ", msgId));
        }
    }

    protected abstract String getFileRelativePath(String infoId, String msgId);

    public abstract Map<String, Object> enrichDisplayInfo(String infoId, String msgId);
}
