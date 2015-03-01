package com.yeapoo.odaesan.sdk.model.menu;

import java.util.Date;

public class ClickButton extends Button {

    private String type;
    private String key;

    public ClickButton() {
        this.type = Type.CLICK;
        this.key = String.valueOf(new Date().getTime());
    }

    public ClickButton(String name) {
        super.setName(name);
        this.type = Type.CLICK;
        this.key = String.valueOf(new Date().getTime());
    }

    public ClickButton(String name, String key) {
        super.setName(name);
        this.type = Type.CLICK;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
