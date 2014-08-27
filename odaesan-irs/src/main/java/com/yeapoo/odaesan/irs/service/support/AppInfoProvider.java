package com.yeapoo.odaesan.irs.service.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.odaesan.irs.dao.AppInfoDao;

@Component
public class AppInfoProvider {

    @Autowired
    private AppInfoDao appInfoDao;

    private Map<String, Map<String, Object>> cache = new ConcurrentHashMap<String, Map<String,Object>>();

    public Map<String, Object> provide(String infoId) {
        Map<String, Object> info = cache.get(infoId);
        if (null == info) {
            info = appInfoDao.get(infoId);
            cache.put(infoId, info);
        }
        return info;
    }
}
