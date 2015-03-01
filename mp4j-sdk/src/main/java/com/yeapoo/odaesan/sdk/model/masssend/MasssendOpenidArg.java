package com.yeapoo.odaesan.sdk.model.masssend;

import java.util.Iterator;
import java.util.List;

public class MasssendOpenidArg {

    private List<String> openidList;
    private String mediaId;
    private String msgtype;

    public MasssendOpenidArg() {}

    public MasssendOpenidArg(List<String> openidList, String mediaId, String msgType) {
        this.openidList = openidList;
        this.mediaId = mediaId;
        this.msgtype = msgType;
    }

    public List<String> getOpenidList() {
        return openidList;
    }

    public void setOpenidList(List<String> openidList) {
        this.openidList = openidList;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    private static final String JSON_TEMPLATE = 
        "{\n" +
            "\"touser\":\n" +
                  "%s,\n" +
            "\"%s\":{\n" +
               "\"%s\": \"%s\"\n" +
            "},\n" +
             "\"msgtype\":\"%s\"\n" +
        "}";

    public String toJSON(boolean isPreview) {
        String result = null;
        if (isPreview) {
            result = openidList.get(0);
        } else {
            Iterator<String> it = openidList.iterator();
            if (!it.hasNext()) {
                result = "[]";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("[\"");
                String e = null;
                while (null != (e = it.next())) {
                    sb.append(e);
                    if (!it.hasNext()) {
                        result = sb.append("\"]").toString();
                        break;
                    } else {
                        sb.append("\",\"");
                    }
                }
            }
        }

        String key = "media_id";
        if ("text".equals(msgtype)) {
            key = "content";
        }

        return String.format(JSON_TEMPLATE, result, msgtype, key, mediaId, msgtype);
    }
}
