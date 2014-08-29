package com.yeapoo.odaesan.api.service.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.MasssendMessageDao;
import com.yeapoo.odaesan.api.dao.UserDao;
import com.yeapoo.odaesan.api.service.MasssendService;
import com.yeapoo.odaesan.api.service.support.AppInfoProvider;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.material.processor.MaterialHandler;
import com.yeapoo.odaesan.sdk.client.MasssendClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendOpenidArg;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendResponse;

@Service
public class MasssendServiceImpl implements MasssendService {

    @Autowired
    private MasssendMessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private MasssendClient masssendClient;

    @Resource(name = "materialHandlers")
    private Map<String, MaterialHandler> materialHandlers;

    private Method masssendByOpenid = ReflectionUtils.findMethod(MasssendClient.class, "masssendByOpenid", new Class<?>[] {Authorization.class, MasssendOpenidArg.class});

    @Override
    public void submitMasssendTask(String infoId, String groupId, String gender, String msgType, String msgId) {
        MaterialHandler handler = materialHandlers.get(msgType);
        Assert.notNull(handler, String.format("invalid msgType %s", msgType));

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        String mediaId = handler.prepareForMasssend(appInfo, msgId, msgType);

        List<String> openidList = null;
        if (StringUtils.hasText(gender)) {
            openidList = userDao.findByGroupAndGender(infoId, groupId, gender);
        } else {
            openidList = userDao.findByGroup(infoId, groupId);
        }
        MasssendOpenidArg body = new MasssendOpenidArg(openidList, mediaId, msgType);
        Object result = adapter.invoke(masssendClient, masssendByOpenid, new Object[] {null, body}, appInfo);

        Assert.notNull(result);
        MasssendResponse response = MasssendResponse.class.cast(result);
        messageDao.insert(infoId, msgType, msgId, response);
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        pagination.setCount(messageDao.count(infoId));
        List<Map<String, Object>> list = messageDao.findAll(infoId, pagination);
        for (Map<String, Object> map : list) {
            String msgType = MapUtil.get(map, "msg_type");
            String msgId = MapUtil.get(map, "msg_id");
            MaterialHandler handler = materialHandlers.get(msgType);
            Map<String, Object> additional = handler.enrichDisplayInfo(infoId, msgId);
            map.putAll(additional);
        }
        return list;
    }

    @Override
    public Map<String, Object> getStatistics(String infoId, String id) {
        return messageDao.getStatistics(infoId, id);
    }

}
