package com.yeapoo.odaesan.sdk.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Ticket;
import com.yeapoo.odaesan.sdk.model.TicketArgs;

@Component
public class QRCodeClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(GroupClient.class);

    @Value("${wx.qrcode.create}")
    private String createURL;
    @Value("${wx.qrcode.redeem}")
    private String redeemURL;

    @SuppressWarnings("unchecked")
    public Ticket requestTicket(Authorization authorization, TicketArgs ticketArgs) throws WeixinSDKException {
        HttpEntity<String> request = new HttpEntity<String>(ticketArgs.toString(), headers);
        String response = template.postForObject(createURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            Map<String, Object> map = mapper.readValue(response, Map.class);
            String ticket = MapUtil.get(map, "ticket");
            Integer expireSeconds = MapUtil.get(map, "expire_seconds", Integer.class);
            if (null == expireSeconds) {
                expireSeconds = 0;
            }
            return new Ticket(ticket, expireSeconds);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    public String redeemTicket(Ticket ticket) {
        return redeemURL.replace("{ticket}", ticket.getTicket());
    }

}
