package com.yeapoo.odaesan.sdk.model.message.event;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class MassSendJobFinishEventMessage extends EventMessage {

    private static final long serialVersionUID = -3035855923613146122L;

    private String status;
    private int totalCount;
    private int filterCount;
    private int sentCount;
    private int errorCount;

    public MassSendJobFinishEventMessage() {}

    public MassSendJobFinishEventMessage(Document document) {
        super(document);
        messageId = XmlUtil.getNodeContent(document, "MsgID");
        status = XmlUtil.getNodeContent(document, Constants.XmlTag.STATUS);
        totalCount = Integer.parseInt(XmlUtil.getNodeContent(document, Constants.XmlTag.TOTAL_COUNT));
        filterCount = Integer.parseInt(XmlUtil.getNodeContent(document, Constants.XmlTag.FILTE_RCOUNT));
        sentCount = Integer.parseInt(XmlUtil.getNodeContent(document, Constants.XmlTag.SENT_COUNT));
        errorCount = Integer.parseInt(XmlUtil.getNodeContent(document, Constants.XmlTag.ERROR_COUNT));
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(int filterCount) {
        this.filterCount = filterCount;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

}
