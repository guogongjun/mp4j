package com.yeapoo.odaesan.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.dao.NewsDao;
import com.yeapoo.odaesan.common.dao.NewsImageDao;
import com.yeapoo.odaesan.common.dao.NewsItemDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.NewsService;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;
    @Autowired
    private NewsItemDao newsItemDao;
    @Autowired
    private NewsImageDao newsImageDao;
    @Autowired
    private StaticResourceHandler handler;

    @Override
    public Map<String, Object> saveImage(String infoId, MultipartFile file) {
        String relativeUrl = handler.handleUploading(infoId, "news", file);
        String id = newsImageDao.insert(infoId, relativeUrl);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("url", handler.getAbsoluteURL(relativeUrl));
        return data;
    }

    @Transactional
    @Override
    public String save(String infoId, Map<String, Object> itemMap) {
        String newsId = newsDao.insert(infoId);
        newsItemDao.insert(infoId, newsId, itemMap);
        return newsId;
    }

    @Transactional
    @Override
    public String save(String infoId, List<Map<String, Object>> itemMapList) {
        String newsId = newsDao.insert(infoId);
        newsItemDao.batchInsert(infoId, newsId, itemMapList);
        return newsId;
    }

    @Override
    public MultiValueMap<String, Map<String, Object>> list(String infoId, Pagination pagination) {
        pagination.setCount(newsDao.count(infoId));
        List<Map<String, Object>> itemMapList = newsDao.findAll(infoId, pagination);
        MultiValueMap<String, Map<String, Object>> organized = new LinkedMultiValueMap<String, Map<String,Object>>();
        for (Map<String, Object> itemMap : itemMapList) {
            String id = MapUtil.getAndRemove(itemMap, "id");
            organized.add(id, itemMap);
        }
        return organized;
    }

    @Override
    public List<Map<String, Object>> get(String infoId, String id) {
        return newsDao.get(infoId, id);
    }

    @Override
    public Map<String, Object> getOneItem(String infoId, String itemId) {
        return newsItemDao.get(infoId, itemId);
    }

    @Transactional
    @Override
    public void update(String infoId, String id, Map<String, Object> updatedItemMap) {
        newsDao.update(infoId, id);
        newsItemDao.deleteByNewsId(infoId, id);
        newsItemDao.insert(infoId, id, updatedItemMap);;
    }

    @Transactional
    @Override
    public void update(String infoId, String id, List<Map<String, Object>> updatedItemMapList) {
        newsDao.update(infoId, id);
        newsItemDao.deleteByNewsId(infoId, id);
        newsItemDao.batchInsert(infoId, id, updatedItemMapList);
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        newsDao.delete(infoId, id);
    }

}
