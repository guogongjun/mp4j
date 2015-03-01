package com.yeapoo.odaesan.sdk.model.message.event;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class ScanEventMessage extends EventMessage {

    private static final long serialVersionUID = 8362868248755671424L;

    private String eventKey;
    private String ticket;

    public ScanEventMessage() {
        super();
    }

    public ScanEventMessage(Document document) {
        super(document);
        eventKey = XmlUtil.getNodeContent(document, Constants.XmlTag.EVENT_KEY);
        ticket = XmlUtil.getNodeContent(document, Constants.XmlTag.TICKET);
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
