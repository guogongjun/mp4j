package com.yeapoo.odaesan.sdk.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class TemplateMessage {

    @JsonProperty("touser")
    private String toUser;
    @JsonProperty("template_id")
    private String templateId;
    private String url;
    @JsonProperty("topcolor")
    private String topColor;
    private Map<String, ColoredValue> data;

    public static class ColoredValue {
        private String value;
        private String color;

        public ColoredValue(String value, String color) {
            super();
            this.value = value;
            this.color = color;
        }

        public ColoredValue(String value) {
            this.value = value;
            this.color = "#173177";
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public Map<String, ColoredValue> getData() {
        return data;
    }

    public void setData(Map<String, ColoredValue> data) {
        this.data = data;
    }

}
