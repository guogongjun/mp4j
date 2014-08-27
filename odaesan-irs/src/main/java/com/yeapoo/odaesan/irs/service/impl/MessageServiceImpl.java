package com.yeapoo.odaesan.irs.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.irs.dao.MessageDao;
import com.yeapoo.odaesan.irs.service.MessageService;
import com.yeapoo.odaesan.irs.service.support.AppInfoProvider;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;
import com.yeapoo.odaesan.sdk.client.MediaClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.LinkMessage;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private MediaClient mediaClient;
    @Autowired
    private StaticResourceHandler handler;

    private Method download = ReflectionUtils.findMethod(MediaClient.class, "download", new Class<?>[] {Authorization.class, String.class});

    @Override
    @Async
    @Transactional
    public AsyncResult<String> save(TextMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String id = messageDao.insert(infoId, message);
        return new AsyncResult<String>(id);
    }

    @Override
    @Transactional
    public void updateKeywordFlag(String infoId, String id, boolean ivrmsg) {
        messageDao.update(infoId, id, ivrmsg);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Async
    @Transactional
    public AsyncResult<String> save(ImageMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String id = messageDao.insert(infoId, message);

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        String mediaId = message.getMediaId();
        Object result = adapter.invoke(mediaClient, download, new Object[] {null, mediaId}, appInfo);

        Assert.notNull(result);
        Map<String, Object> fileInfo = Map.class.cast(result);
        String url = handler.handleDownloading(infoId, message.getMessageType(), fileInfo);

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[] {id, infoId, "media_id", mediaId});
        batchArgs.add(new Object[] {id, infoId, "pic_url", message.getPicUrl()});
        batchArgs.add(new Object[] {id, infoId, "url", url});
        messageDao.insertAdditionalInfo(batchArgs);

        return new AsyncResult<String>(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Async
    @Transactional
    public AsyncResult<String> save(VoiceMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String id = messageDao.insert(infoId, message);

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        String mediaId = message.getMediaId();
        Object result = adapter.invoke(mediaClient, download, new Object[] {null, mediaId}, appInfo);

        Assert.notNull(result);
        Map<String, Object> fileInfo = Map.class.cast(result);
        String url = handler.handleDownloading(infoId, message.getMessageType(), fileInfo);

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[] {id, infoId, "media_id", mediaId});
        batchArgs.add(new Object[] {id, infoId, "url", url});
        batchArgs.add(new Object[] {id, infoId, "format", message.getFormat()});
        batchArgs.add(new Object[] {id, infoId, "recognition", message.getRecognition()});
        messageDao.insertAdditionalInfo(batchArgs);

        return new AsyncResult<String>(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Async
    @Transactional
    public AsyncResult<String> save(VideoMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String id = messageDao.insert(infoId, message);

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        // 处理视频
        String mediaId = message.getMediaId();
        Object result = adapter.invoke(mediaClient, download, new Object[] {null, mediaId}, appInfo);
        Assert.notNull(result);
        Map<String, Object> fileInfo = Map.class.cast(result);
        String url = handler.handleDownloading(infoId, message.getMessageType(), fileInfo);
        // 处理视频缩略图
        String thumbMediaId = message.getThumbMediaId();
        result = adapter.invoke(mediaClient, download, new Object[] {null, thumbMediaId}, appInfo);
        Assert.notNull(result);
        fileInfo = Map.class.cast(result);
        String thumbUrl = handler.handleDownloading(infoId, "video/thumb", fileInfo);

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[] {id, infoId, "media_id", mediaId});
        batchArgs.add(new Object[] {id, infoId, "url", url});
        batchArgs.add(new Object[] {id, infoId, "thumb_media_id", thumbMediaId});
        batchArgs.add(new Object[] {id, infoId, "thumb_url", thumbUrl});
        batchArgs.add(new Object[] {id, infoId, "title", message.getTitle()});
        batchArgs.add(new Object[] {id, infoId, "description", message.getDescription()});
        messageDao.insertAdditionalInfo(batchArgs);

        return new AsyncResult<String>(id);
    }

    @Override
    @Async
    @Transactional
    public AsyncResult<String> save(LocationMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String id = messageDao.insert(infoId, message);

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[] {id, infoId, "latitude", message.getLatitude()});
        batchArgs.add(new Object[] {id, infoId, "longitude", message.getLongitude()});
        batchArgs.add(new Object[] {id, infoId, "scale", message.getScale()});
        batchArgs.add(new Object[] {id, infoId, "label", message.getLabel()});
        messageDao.insertAdditionalInfo(batchArgs);

        return new AsyncResult<String>(id);
    }

    @Override
    @Async
    @Transactional
    public AsyncResult<String> save(LinkMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        String id = messageDao.insert(infoId, message);

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[] {id, infoId, "url", message.getUrl()});
        batchArgs.add(new Object[] {id, infoId, "title", message.getTitle()});
        batchArgs.add(new Object[] {id, infoId, "description", message.getDescription()});
        messageDao.insertAdditionalInfo(batchArgs);

        return new AsyncResult<String>(id);
    }

}
