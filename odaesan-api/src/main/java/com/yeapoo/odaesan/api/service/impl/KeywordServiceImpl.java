package com.yeapoo.odaesan.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeapoo.odaesan.api.dao.KeywordDao;
import com.yeapoo.odaesan.api.dao.KeywordGroupDao;
import com.yeapoo.odaesan.api.service.KeywordService;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.model.Pagination;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordDao keywordDao;
    @Autowired
    private KeywordGroupDao groupDao;

    @Transactional
    @Override
    public String save(String infoId, String ruleName, List<Map<String, Object>> keywordList, String replyId, String replyType) {
        String groupId = groupDao.insert(infoId, ruleName, replyId, replyType);
        if (null != keywordList) {
            keywordDao.batchInsert(groupId, keywordList);
        }
        return groupId;
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        pagination.setCount(keywordDao.count(infoId));
        return keywordDao.list(infoId, pagination);
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        Map<String, Object> data = groupDao.get(infoId, id);
        if (null != data) {
            List<Map<String, Object>> keywords = keywordDao.get(infoId, id);
            data.put("keyword", keywords);
        }
        return data;
    }

    @Override
    public Map<String, Object> getSubscribeInfo(String infoId) {
        return groupDao.getInfo(infoId, Constants.Keyword.SUBSCRIBE);
    }

    @Override
    public Map<String, Object> getDefaultInfo(String infoId) {
        return groupDao.getInfo(infoId, Constants.Keyword.DEFAULT);
    }

    @Transactional
    @Override
    public void update(String infoId, String id, String ruleName, List<Map<String, Object>> keywordList, String replyId, String replyType) {
        groupDao.update(infoId, id, ruleName, replyId, replyType);
        if (null != keywordList) {
            keywordDao.delete(id);
            keywordDao.batchInsert(id, keywordList);
        }
    }

    @Transactional
    @Override
    public void delete(String infoId, String id) {
        groupDao.delete(infoId, id);
    }

}
