package com.yeapoo.odaesan.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.dao.VideoDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.VideoService;
import com.yeapoo.odaesan.common.support.AppInfoProvider;
import com.yeapoo.odaesan.material.support.AsyncUploader;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;
import com.yeapoo.odaesan.sdk.constants.Constants;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private AsyncUploader uploader;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private StaticResourceHandler handler;

    @Transactional
    @Override
    public Map<String, Object> save(String infoId, MultipartFile file) {
        String relativeUrl = handler.handleUploading(infoId, "video", file);
        String id = videoDao.insertUrl(infoId, relativeUrl);

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        uploader.uploadVideoToWeixin(appInfo, id, Constants.MaterialType.VIDEO);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("url", handler.getAbsoluteURL(relativeUrl));
        return data;
    }

    @Transactional
    @Override
    public void update(String infoId, String id, String title, String description) {
        videoDao.update(infoId, id, title, description);
    }

    @Transactional
    @Override
    public Map<String, Object> updateMedia(String infoId, String id, MultipartFile file) {
        String relativeUrl = handler.handleUploading(infoId, "video", file);
        videoDao.updateUrl(infoId, id, relativeUrl);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("url", handler.getAbsoluteURL(relativeUrl));
        return data;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        pagination.setCount(videoDao.count(infoId));
        List<Map<String, Object>> list = videoDao.findAll(infoId, pagination);
        return list;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        Map<String, Object> videoInfo = videoDao.get(infoId, id);
        videoInfo.put("url", handler.getAbsoluteURL(MapUtil.get(videoInfo, "url")));
        return videoInfo;
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        videoDao.delete(infoId, id);
    }

}
