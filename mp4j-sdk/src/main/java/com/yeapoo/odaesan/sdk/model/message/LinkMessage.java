package com.yeapoo.odaesan.sdk.model.message;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class LinkMessage extends Message {

    private static final long serialVersionUID = -3296090259320957299L;

    private String title;
    private String description;
    private String url;

    public LinkMessage() {
        this.setMessageType(Constants.MessageType.LINK);
    }

    public LinkMessage(Document document) {
        super(document);
        title = XmlUtil.getNodeContent(document, Constants.XmlTag.TITLE);
        description = XmlUtil.getNodeContent(document, Constants.XmlTag.DESCRIPTION);
        url = XmlUtil.getNodeContent(document, Constants.XmlTag.URL);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
