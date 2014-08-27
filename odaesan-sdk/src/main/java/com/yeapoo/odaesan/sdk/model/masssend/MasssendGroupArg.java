package com.yeapoo.odaesan.sdk.model.masssend;

public class MasssendGroupArg {

    private int groupId;
    private String mediaId;
    private String msgtype;

    public MasssendGroupArg() {}

    public MasssendGroupArg(int groupId, String mediaId, String msgtype) {
        this.groupId = groupId;
        this.mediaId = mediaId;
        this.msgtype = msgtype;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
               "\"filter\":{\n" +
                  "\"group_id\":\"%d\"\n" +
               "},\n" +
               "\"%s\":{\n" +
                  "\"media_id\":\"%s\"\n" +
               "},\n" +
                "\"msgtype\":\"%s\"\n" +
            "}";

    public String toJSON() {
        return String.format(JSON_TEMPLATE, groupId, msgtype, mediaId, msgtype);
    }
}
