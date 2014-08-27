package com.yeapoo.odaesan.irs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.irs.dao.KeywordDao;
import com.yeapoo.odaesan.irs.service.KeywordService;
import com.yeapoo.odaesan.irs.service.message.MessageConstructor;
import com.yeapoo.odaesan.irs.service.support.AppInfoProvider;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordDao keywordDao;
    @Autowired
    private AppInfoProvider infoProvider;

    @Resource(name="msgConstructors")
    private Map<String, MessageConstructor> msgConstructors;

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
