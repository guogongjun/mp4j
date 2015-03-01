package com.yeapoo.odaesan.sdk.model.menu;

public class ViewButton extends Button {

    private String type;
    private String url;

    public ViewButton() {
        this.type = Type.VIEW;
    }

    public ViewButton(String name, String url) {
        super.setName(name);
        this.type = Type.VIEW;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

}
