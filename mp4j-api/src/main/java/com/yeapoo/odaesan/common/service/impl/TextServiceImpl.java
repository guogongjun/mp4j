package com.yeapoo.odaesan.common.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeapoo.odaesan.common.dao.TextDao;
import com.yeapoo.odaesan.common.service.TextService;

@Service
public class TextServiceImpl implements TextService {

    @Autowired
    private TextDao textDao;

    @Transactional
    @Override
    public String save(String infoId, String content) {
        return textDao.save(infoId, content);
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        return textDao.get(infoId, id);
    }

}
