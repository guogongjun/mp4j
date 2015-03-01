package com.yeapoo.odaesan.sdk.model.message;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class ImageMessage extends Message {

    private static final long serialVersionUID = 1032921858876745237L;

    private String picUrl;
    private String mediaId;

    public ImageMessage() {
        this.setMessageType(Constants.MessageType.IMAGE);
    }

    public ImageMessage(String mediaId) {
        this.mediaId = mediaId;
        this.setMessageType(Constants.MessageType.IMAGE);
    }

    public ImageMessage(Message receivedMessage) {
        super(receivedMessage);
        this.setMessageType(Constants.MessageType.IMAGE);
    }

    public ImageMessage(Message receivedMessage, String mediaId) {
        super(receivedMessage);
        this.setMessageType(Constants.MessageType.IMAGE);
        this.mediaId = mediaId;
    }

    public ImageMessage(Document document) {
        super(document);
        picUrl = XmlUtil.getNodeContent(document, Constants.XmlTag.PIC_URL);
        mediaId = XmlUtil.getNodeContent(document, Constants.XmlTag.MEDIA_ID);
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    private static final String XMLTemplate = "<Image><MediaId><![CDATA[%s]]></MediaId></Image>";
    private static final String JSONTemplate = "\"media_id\": \"%s\"";

    @Override
    public String toXML() {
        String baseTemplate = super.toXML();
        String data = String.format(XMLTemplate, mediaId);
        return baseTemplate.replace(Constants.TEMPLATE_REEPLACE_STR, data);
    }

    @Override
    public String toJSON() {
        String baseTemplate = super.toJSON();
        String data = String.format(JSONTemplate, mediaId);
        return baseTemplate.replace(Constants.TEMPLATE_REEPLACE_STR, data);
    }
}
