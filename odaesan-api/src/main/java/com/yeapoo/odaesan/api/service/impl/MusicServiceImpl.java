package com.yeapoo.odaesan.api.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.api.service.MusicService;
import com.yeapoo.odaesan.common.model.Pagination;

@Service
public class MusicServiceImpl implements MusicService {

    @Override
    public Map<String, Object> save(String infoId, MultipartFile file) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream get(String infoId, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void rename(String infoId, String id, String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String infoId, String id) {
        // TODO Auto-generated method stub

    }

}
