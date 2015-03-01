package com.yeapoo.odaesan.common.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.dao.MasssendMessageDao;
import com.yeapoo.odaesan.common.dao.UserDao;
import com.yeapoo.odaesan.common.exception.APIException;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.MasssendService;
import com.yeapoo.odaesan.common.support.AppInfoProvider;
import com.yeapoo.odaesan.material.processor.MaterialHandler;
import com.yeapoo.odaesan.sdk.client.MasssendClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendOpenidArg;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendResponse;

@Service
public class MasssendServiceImpl implements MasssendService {

    @Autowired
    private MasssendMessageDao masssendDao;
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
    private Method previewMasssend = ReflectionUtils.findMethod(MasssendClient.class, "previewMasssend", new Class<?>[] {Authorization.class, MasssendOpenidArg.class});

    @Override
    public void submitMasssendTask(String infoId, String groupId, String gender, String msgType, String msgId) {
        MaterialHandler handler = materialHandlers.get(msgType);
        Assert.notNull(handler, String.format("invalid msgType %s", msgType));

        // 先进行群发人数的判断
        List<String> openidList = null;
        if (StringUtils.hasText(gender)) {
            openidList = userDao.findByGroupAndGender(infoId, groupId, gender);
        } else {
            openidList = userDao.findByGroup(infoId, groupId);
        }
        if (null == openidList || openidList.isEmpty()) {
            throw new APIException("invalid openid list under selection condition");
        }

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        String mediaId = handler.prepareForMasssend(appInfo, msgId, msgType);

        MasssendOpenidArg body = new MasssendOpenidArg(openidList, mediaId, msgType);
        Object result = adapter.invoke(masssendClient, masssendByOpenid, new Object[] {null, body}, appInfo);

        Assert.notNull(result);
        MasssendResponse response = MasssendResponse.class.cast(result);
        masssendDao.insert(infoId, msgType, msgId, response);
    }

    @Override
    public void sendPreviewMessage(String infoId, String openid, String msgType, String msgId) {
        MaterialHandler handler = materialHandlers.get(msgType);
        Assert.notNull(handler, String.format("invalid msgType %s", msgType));

        Assert.hasText(openid, String.format("openid must exists"));
        List<String> singleOpenid = new ArrayList<String>(1);
        singleOpenid.add(openid);

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        String mediaId = handler.prepareForMasssend(appInfo, msgId, msgType);

        MasssendOpenidArg body = new MasssendOpenidArg(singleOpenid, mediaId, msgType);
        Object result = adapter.invoke(masssendClient, previewMasssend, new Object[] {null, body}, appInfo);
        Assert.notNull(result);
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        int count = masssendDao.count(infoId);//XXX 此处还是有总数不准确的问题
        List<Map<String, Object>> list = null;
        if (count != 0) {
            list = masssendDao.findAll(infoId, pagination);
            Iterator<Map<String, Object>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = iterator.next();
                String msgType = MapUtil.get(map, "msg_type");
                String msgId = MapUtil.get(map, "msg_id");
                MaterialHandler handler = materialHandlers.get(msgType);
                Map<String, Object> additional = handler.enrichDisplayInfo(infoId, msgId);
                if (null == additional) {
                    iterator.remove();
                    count--;
                } else {
                    map.putAll(additional);
                }
            }
        }
        pagination.setCount(count);
        return list;
    }

    @Override
    public Map<String, Object> getStatistics(String infoId, String id) {
        return masssendDao.getStatistics(infoId, id);
    }

    @Override
    @Transactional
    public void updateStatistics(String infoId, String wxMsgId, String status, int totalCount, int filterCount, int sentCount, int errorCount) {
        masssendDao.updateStatistics(infoId, wxMsgId, status, totalCount, filterCount, sentCount, errorCount);
    }

}
