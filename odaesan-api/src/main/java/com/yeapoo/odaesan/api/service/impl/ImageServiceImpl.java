package com.yeapoo.odaesan.api.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.ImageDao;
import com.yeapoo.odaesan.api.service.ImageService;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private StaticResourceHandler handler;

    @Transactional
    @Override
    public Map<String, Object> save(String infoId, MultipartFile file) {
        String relativeUrl = handler.handleUploading(infoId, "image", file);
        String name = file.getOriginalFilename();
        String id = imageDao.insert(infoId, name, relativeUrl);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("name", name);
        data.put("url", handler.getAbsoluteURL(relativeUrl));
        return data;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        List<Map<String, Object>> list = imageDao.findAll(infoId, pagination);
        pagination.setCount(imageDao.count(infoId));
        return list;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        return imageDao.get(infoId, id);
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
