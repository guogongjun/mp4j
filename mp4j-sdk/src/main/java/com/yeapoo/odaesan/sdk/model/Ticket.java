package com.yeapoo.odaesan.sdk.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ticket {

    private String ticket;
    @JsonProperty(value = "expire_seconds")
    private int expireSeconds;

    public Ticket() {}

    public Ticket(String ticket, int expireSeconds) {
        this.ticket = ticket;
        this.expireSeconds = expireSeconds;
    }

    public String getTicket() {
        return ticket;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }
}
