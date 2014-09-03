package com.yeapoo.odaesan.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.MessageDao;
import com.yeapoo.odaesan.api.service.MessageService;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private StaticResourceHandler handler;

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

}
