package com.yeapoo.odaesan.sdk.model.message;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class VideoMessage extends Message {

    private static final long serialVersionUID = -6129597916639113074L;

    private String mediaId;
    private String thumbMediaId;
    private String title;
    private String description;

    public VideoMessage() {
        this.setMessageType(Constants.MessageType.VIDEO);
    }

    public VideoMessage(Message receivedMessage) {
        super(receivedMessage);
        this.setMessageType(Constants.MessageType.VIDEO);
    }

    public VideoMessage(Document document) {
        super(document);
        mediaId = XmlUtil.getNodeContent(document, Constants.XmlTag.MEDIA_ID);
        thumbMediaId = XmlUtil.getNodeContent(document, Constants.XmlTag.THUMB_MEDIA_ID);
    }

    public VideoMessage(String mediaId, String title, String description) {
        this.mediaId = mediaId;
        this.title = title;
        this.description = description;
        this.setMessageType(Constants.MessageType.VIDEO);
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static final String XMLTemplate =
            "<Video>" +
            "<MediaId><![CDATA[%s]]></MediaId>" +
            "<Title><![CDATA[%s]]></Title>" +
            "<Description><![CDATA[%s]]></Description>" +
            "</Video>";
    private static final String JSONTemplate = "\"media_id\": \"%s\", \"title\": \"%s\", \"description\": \"%s\"";

    @Override
    public String toXML() {
        String baseTemplate = super.toXML();
        String data = String.format(XMLTemplate, mediaId, title, description);
        return baseTemplate.replace(Constants.TEMPLATE_REEPLACE_STR, data);
    }

    @Override
    public String toJSON() {
        String baseTemplate = super.toJSON();
        String data = String.format(JSONTemplate, mediaId, title, description);
        return baseTemplate.replace(Constants.TEMPLATE_REEPLACE_STR, data);
    }
}
