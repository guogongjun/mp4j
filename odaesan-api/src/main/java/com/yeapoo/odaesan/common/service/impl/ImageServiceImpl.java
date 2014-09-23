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
import com.yeapoo.odaesan.common.dao.ImageDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.ImageService;
import com.yeapoo.odaesan.common.support.AppInfoProvider;
import com.yeapoo.odaesan.material.support.AsyncUploader;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;
import com.yeapoo.odaesan.sdk.constants.Constants;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private AsyncUploader uploader;
    @Autowired
    private AppInfoProvider infoProvider;
    @Autowired
    private StaticResourceHandler handler;

    @Transactional
    @Override
    public Map<String, Object> save(String infoId, MultipartFile file) {
        String relativeUrl = handler.handleUploading(infoId, "image", file);
        String name = file.getOriginalFilename();
        String id = imageDao.insert(infoId, name, relativeUrl);

        Map<String, Object> appInfo = infoProvider.provide(infoId);
        uploader.uploadVideoToWeixin(appInfo, id, Constants.MaterialType.IMAGE);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("name", name);
        data.put("url", handler.getAbsoluteURL(relativeUrl));
        return data;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        pagination.setCount(imageDao.count(infoId));
        List<Map<String, Object>> list = imageDao.findAll(infoId, pagination);
        return list;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        Map<String, Object> imageInfo = imageDao.get(infoId, id);
        imageInfo.put("url", handler.getAbsoluteURL(MapUtil.get(imageInfo, "url")));
        return imageInfo;
    }

    @Override
    public Map<String, Object> getWithStream(String infoId, String id) {
        Map<String, Object> data = imageDao.get(infoId, id);
        InputStream stream = handler.handleDownloading(MapUtil.get(data, "url"));
        data.put("stream", stream);
        return data;
    }

    @Transactional
    @Override
    public void rename(String infoId, String id, String name) {
        imageDao.update(infoId, id, name);
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        imageDao.delete(infoId, id);
    }

}
