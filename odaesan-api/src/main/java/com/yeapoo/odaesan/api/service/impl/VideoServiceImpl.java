package com.yeapoo.odaesan.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.api.service.VideoService;
import com.yeapoo.odaesan.common.model.Pagination;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public Map<String, Object> save(String infoId, MultipartFile file) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String save(String infoId, Map<String, Object> itemMap) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(String infoId, String id, Map<String, Object> updatedItemMap) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String infoId, String id) {
        // TODO Auto-generated method stub

    }

}
