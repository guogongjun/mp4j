package com.yeapoo.odaesan.common.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.dao.MessageDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.MessageService;
import com.yeapoo.odaesan.common.support.AppInfoProvider;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;
import com.yeapoo.odaesan.sdk.client.MediaClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.LinkMessage;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;
import com.yeapoo.odaesan.sdk.model.message.event.ClickEventMessage;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private MediaClient mediaClient;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private StaticResourceHandler handler;

    private Method download = ReflectionUtils.findMethod(MediaClient.class, "download", new Class<?>[] {Authorization.class, String.class});

    @Override
    public List<Map<String, Object>> list(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter, Pagination pagination) {
        pagination.setCount(messageDao.count(infoId, startDate, endDate, filterivrmsg, filter));
        List<Map<String, Object>> list = messageDao.list(infoId, startDate, endDate, filterivrmsg, filter, pagination);
        String msgId = null;
        List<Map<String, Object>> additionalInfo = null;
        Map<String, Object> organized = null;
        for (Map<String, Object> map : list) {
            msgId = MapUtil.get(map, "id");
            if (!"text".equals(MapUtil.get(map, "type"))) {
                additionalInfo = messageDao.getAdditionalInfo(infoId, msgId);
                organized = organize(additionalInfo);
                map.putAll(organized);
            }
        }
        return list;
    }

    private Map<String, Object> organize(List<Map<String, Object>> additionalInfo) {
        Map<String, Object> organized = new HashMap<String, Object>();
        for (Map<String, Object> map : additionalInfo) {
            String key = MapUtil.get(map, "meta_key");
            Object value = map.get("meta_value");
            if ("url".equals(key)) {
                value = handler.getAbsoluteURL(value.toString());
            }
            organized.put(key, value);
        }
        return organized;
    }

    @Override
    @Transactional
    public String save(TextMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        return messageDao.insert(infoId, message);
    }

    @Override
    @Transactional
    public void save(ClickEventMessage message, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");
        messageDao.insert(infoId, message);
    }

    @Async
    @Override
    @Transactional
    public void updateKeywordFlag(String infoId, String id, boolean ivrmsg) {
        messageDao.update(infoId, id, ivrmsg);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Async
    @Transactional
    public Future<String> save(ImageMessage message, Map<String, Object> params) {
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
    public Future<String> save(VoiceMessage message, Map<String, Object> params) {
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
    public Future<String> save(VideoMessage message, Map<String, Object> params) {
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
    public Future<String> save(LocationMessage message, Map<String, Object> params) {
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
    public Future<String> save(LinkMessage message, Map<String, Object> params) {
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
