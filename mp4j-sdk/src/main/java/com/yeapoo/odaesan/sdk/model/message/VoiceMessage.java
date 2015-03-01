package com.yeapoo.odaesan.sdk.model.message;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class VoiceMessage extends Message {

    private static final long serialVersionUID = 1654393912545923974L;

    private String mediaId;
    private String format;
    private String recognition;

    public VoiceMessage() {
        this.setMessageType(Constants.MessageType.VOICE);
    }

    public VoiceMessage(String mediaId) {
        this.mediaId = mediaId;
        this.setMessageType(Constants.MessageType.IMAGE);
    }

    public VoiceMessage(Message receivedMessage) {
        super(receivedMessage);
        this.setMessageType(Constants.MessageType.VOICE);
    }

    public VoiceMessage(Message receivedMessage, String mediaId) {
        super(receivedMessage);
        this.mediaId = mediaId;
        this.setMessageType(Constants.MessageType.VOICE);
    }


    public VoiceMessage(Document document) {
        super(document);
        mediaId = XmlUtil.getNodeContent(document, Constants.XmlTag.MEDIA_ID);
        format = XmlUtil.getNodeContent(document, Constants.XmlTag.FORMAT);
        recognition = XmlUtil.getNodeContent(document, Constants.XmlTag.RECOGNITION);
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    private static final String XMLTemplate = "<Voice><MediaId><![CDATA[%s]]></MediaId></Voice>";
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
