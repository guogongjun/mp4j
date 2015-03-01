package com.yeapoo.odaesan.sdk.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Media {

    private String type;
    @JsonProperty(value = "media_id")
    private String mediaId;
    @JsonProperty(value = "created_at")
    private int createdAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public int getCreatedAt() {
        return createdAt;
    }
}
