package com.yeapoo.odaesan.sdk.model.message;

import java.io.Serializable;
import java.util.Date;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public abstract class Message implements Serializable {

    private static final long serialVersionUID = 3603509452147684251L;
    private String fromUserName;
    private String toUserName;
    private long createTime;
    private String messageType;
    protected String messageId;

    public Message() {};

    public Message(Document document) {
        fromUserName = XmlUtil.getNodeContent(document, Constants.XmlTag.FROM_USER_NAME);
        toUserName = XmlUtil.getNodeContent(document, Constants.XmlTag.TO_USER_NAME);
        createTime = Long.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.CREATE_TIME));
        messageType = XmlUtil.getNodeContent(document, Constants.XmlTag.MSG_TYPE);
        messageId = XmlUtil.getNodeContent(document, Constants.XmlTag.MSG_ID);
    }

    public Message(Message receivedMessage) {
        fromUserName = receivedMessage.toUserName;
        toUserName = receivedMessage.fromUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public Date getCreateTime() {
        return new Date(createTime * 1000);
    }

    public String getMessageType() {
        return messageType;
    }

    protected void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    private static String messageXMLTemplate =
        "<xml>" + "\n" +
        "<FromUserName><![CDATA[%s]]></FromUserName>" + "\n" +
        "<ToUserName><![CDATA[%s]]></ToUserName>" + "\n" +
        "<CreateTime>%s</CreateTime>" + "\n" +
        "<MsgType><![CDATA[%s]]></MsgType>" + "\n" +
        "%s" + "\n" +
        "</xml>";

    private static String messageJSONTemplate =
        "{" + "\n" +
        "  \"touser\": \"%s\"," + "\n" +
        "  \"msgtype\": \"%s\"," + "\n" +
        "  \"%s\":" + "\n" +
        "    {" + "\n" +
        "        %s" + "\n" +
        "    }" + "\n" +
        "}";

    public String toXML() {
        createTime = new Date().getTime() / 1000;
        return String.format(messageXMLTemplate, fromUserName, toUserName, createTime, messageType, Constants.TEMPLATE_REEPLACE_STR);
    }

    public String toJSON() {
        return String.format(messageJSONTemplate, toUserName, messageType, messageType, Constants.TEMPLATE_REEPLACE_STR);
    }
}
