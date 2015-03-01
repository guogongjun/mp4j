package com.yeapoo.odaesan.sdk.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class FollowerContainer {

    private int total;

    private int count;

    private Map<String, Object> data;

    @JsonProperty(value = "next_openid")
    private String nextOpenid;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getNextOpenid() {
        return nextOpenid;
    }

    public void setNextOpenid(String nextOpenid) {
        this.nextOpenid = nextOpenid;
    }

    @SuppressWarnings("unchecked")
    public List<String> getOpenids() {
        return List.class.cast(data.get("openid"));
    }

}
