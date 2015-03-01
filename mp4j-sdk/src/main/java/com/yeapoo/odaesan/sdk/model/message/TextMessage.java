package com.yeapoo.odaesan.sdk.model.message;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class TextMessage extends Message {

    private static final long serialVersionUID = 2842486242504602375L;

    private String content;

    public TextMessage() {
        super.setMessageType(Constants.MessageType.TEXT);
    }

    public TextMessage(String content) {
        this.content = content;
        super.setMessageType(Constants.MessageType.TEXT);
    }

    public TextMessage(Message receivedMessage) {
        super(receivedMessage);
        super.setMessageType(Constants.MessageType.TEXT);
    }

    public TextMessage(Message receivedMessage, String content) {
        super(receivedMessage);
        super.setMessageType(Constants.MessageType.TEXT);
        this.content = content;
    }

    public TextMessage(Document document) {
        super(document);
        content = XmlUtil.getNodeContent(document, Constants.XmlTag.CONTENT);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    private static final String XMLTemplate = "<Content><![CDATA[%s]]></Content>";
    private static final String JSONTemplate = "\"content\": \"%s\"";

    @Override
    public String toXML() {
        String baseTemplate = super.toXML();
        String data = String.format(XMLTemplate, content);
        return baseTemplate.replace(Constants.TEMPLATE_REEPLACE_STR, data);
    }

    @Override
    public String toJSON() {
        String baseTemplate = super.toJSON();
        String data = String.format(JSONTemplate, content);
        return baseTemplate.replace(Constants.TEMPLATE_REEPLACE_STR, data);
    }

}
