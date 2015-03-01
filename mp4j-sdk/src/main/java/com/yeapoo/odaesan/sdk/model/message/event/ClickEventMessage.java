package com.yeapoo.odaesan.sdk.model.message.event;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class ClickEventMessage extends EventMessage {

    private static final long serialVersionUID = -1675757754209698404L;

    private String eventKey;

    public ClickEventMessage() {
        super();
    }

    public ClickEventMessage(Document document) {
        super(document);
        eventKey = XmlUtil.getNodeContent(document, Constants.XmlTag.EVENT_KEY);
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

}
