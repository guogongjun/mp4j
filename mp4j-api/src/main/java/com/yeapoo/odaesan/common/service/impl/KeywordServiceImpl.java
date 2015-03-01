package com.yeapoo.odaesan.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.dao.KeywordDao;
import com.yeapoo.odaesan.common.dao.KeywordGroupDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.KeywordService;
import com.yeapoo.odaesan.common.support.AppInfoProvider;
import com.yeapoo.odaesan.irs.message.MessageConstructor;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordDao keywordDao;
    @Autowired
    private KeywordGroupDao groupDao;
    @Autowired
    private AppInfoProvider infoProvider;

    @Resource(name="msgConstructors")
    private Map<String, MessageConstructor> msgConstructors;

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

    @Override
    public Message getReplyByKeyword(String infoId, String content, Message input) {
        // 先进行精确查找
        Map<String, Object> replyMsgInfo = keywordDao.find(infoId, content);
        if (null == replyMsgInfo) { // 进行模糊查询
            replyMsgInfo = keywordDao.findFuzzy(infoId, content);
        }
        if (null == replyMsgInfo) { // 进行分词后的模糊查询
            List<Term> terms = ToAnalysis.parse(content);
            for (Term term : terms) {
                replyMsgInfo = keywordDao.findFuzzy(infoId, term.toString());
                if (null != replyMsgInfo) {
                    break;
                }
            }
        }

        if (null == replyMsgInfo) {
            return null;
        }

        String replyId = MapUtil.get(replyMsgInfo, "reply_id");
        String replyType = MapUtil.get(replyMsgInfo, "reply_type");
        Map<String, Object> appInfo = infoProvider.provide(infoId);
        MessageConstructor constructor = msgConstructors.get(replyType);
        return constructor.construct(replyId, input, appInfo);
    }

    @Override
    public Message getReplyByName(String infoId, String ruleName, Message input) {
        Map<String, Object> replyMsgInfo = keywordDao.findByName(infoId, ruleName);

        if (null == replyMsgInfo) {
            return null;
        }

        String replyId = MapUtil.get(replyMsgInfo, "reply_id");
        String replyType = MapUtil.get(replyMsgInfo, "reply_type");
        Map<String, Object> appInfo = infoProvider.provide(infoId);
        MessageConstructor constructor = msgConstructors.get(replyType);
        return constructor.construct(replyId, input, appInfo);
    }

}
