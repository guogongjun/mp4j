package com.yeapoo.odaesan.common.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.dao.VoiceDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.VoiceService;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;

@Service
public class VoiceServiceImpl implements VoiceService {

    @Autowired
    private VoiceDao voiceDao;
    @Autowired
    private StaticResourceHandler handler;

    @Transactional
    @Override
    public Map<String, Object> save(String infoId, MultipartFile file) {
        String relativeUrl = handler.handleUploading(infoId, "voice", file);
        String name = file.getOriginalFilename();
        String id = voiceDao.insert(infoId, name, relativeUrl);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("name", name);
        data.put("url", handler.getAbsoluteURL(relativeUrl));
        return data;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        pagination.setCount(voiceDao.count(infoId));
        List<Map<String, Object>> list = voiceDao.findAll(infoId, pagination);
        return list;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        Map<String, Object> voiceInfo = voiceDao.get(infoId, id);
        voiceInfo.put("url", handler.getAbsoluteURL(MapUtil.get(voiceInfo, "url")));
        return voiceInfo;
    }

    @Override
    public Map<String, Object> getWithStream(String infoId, String id) {
        Map<String, Object> data = voiceDao.get(infoId, id);
        InputStream stream = handler.handleDownloading(MapUtil.get(data, "url"));
        data.put("stream", stream);
        return data;
    }

    @Transactional
    @Override
    public void rename(String infoId, String id, String name) {
        voiceDao.update(infoId, id, name);
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        voiceDao.delete(infoId, id);
    }

}
