package com.yeapoo.odaesan.sdk.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Simon
 *
 */
public class Authorization implements Serializable {

    private static final long serialVersionUID = 3637977842545040092L;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private long expiresIn;
    private long lastRequestTime;

    public Authorization(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.lastRequestTime = new Date().getTime();
    }

    public Authorization() {
        this.lastRequestTime = new Date().getTime();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public boolean isExpired() {
        long now = new Date().getTime();
        long timePassed = now - lastRequestTime;
        long remaining = expiresIn - timePassed / 1000;
        return remaining < 10; // 预留10秒钟
    }
}
