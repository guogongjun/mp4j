package com.yeapoo.odaesan.irs.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.irs.dao.MenuDao;
import com.yeapoo.odaesan.irs.service.MenuService;
import com.yeapoo.odaesan.irs.service.message.MessageConstructor;
import com.yeapoo.odaesan.irs.service.support.AppInfoProvider;
import com.yeapoo.odaesan.sdk.model.message.Message;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AppInfoProvider infoProvider;

    @Resource(name="msgConstructors")
    private Map<String, MessageConstructor> msgConstructors;

    @Override
    public Message getReplyByKeycode(String infoId, String eventKey, Message input) {
        Map<String, Object> replyMsgInfo = menuDao.getByKeycode(infoId, eventKey);

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
