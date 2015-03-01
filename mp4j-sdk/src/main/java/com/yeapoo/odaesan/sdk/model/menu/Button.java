package com.yeapoo.odaesan.sdk.model.menu;

public class Button {

    private String name;

    public interface Type {
        String CLICK = "click";
        String VIEW = "view";
    }

    public Button() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
