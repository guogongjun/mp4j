package com.yeapoo.odaesan.irs.resolver;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.framework.resolver.ResolverAdapter;
import com.yeapoo.odaesan.irs.service.QRCodeService;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.event.ScanEventMessage;

@Component
public class ScanResolver extends ResolverAdapter<ScanEventMessage> {

    private static final int BEGIN_INDEX = "qrscene_".length();

    @Autowired
    private QRCodeService qrcodeService;

    @Override
    public Message resolve(ScanEventMessage input, Map<String, Object> params) {
        String infoId = MapUtil.get(params, "info_id");

        String event = input.getEvent();
        String sceneId = input.getEventKey();
        boolean isNewFollower = "subscribe".equals(event);
        if (isNewFollower) {
            sceneId = sceneId.substring(BEGIN_INDEX);
        }

        String openid = input.getFromUserName();
        String ticket = input.getTicket();
        Date createTime = input.getCreateTime();
        qrcodeService.saveScanInfo(infoId, sceneId, openid, ticket, isNewFollower, createTime);
        return null;
    }
}
