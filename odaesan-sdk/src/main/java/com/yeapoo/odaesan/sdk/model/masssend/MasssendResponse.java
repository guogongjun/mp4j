package com.yeapoo.odaesan.sdk.model.masssend;

import org.codehaus.jackson.annotate.JsonProperty;

import com.yeapoo.odaesan.sdk.model.ErrorResponse;

public class MasssendResponse extends ErrorResponse {

    @JsonProperty("msg_id")
    private long msgId;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

}
