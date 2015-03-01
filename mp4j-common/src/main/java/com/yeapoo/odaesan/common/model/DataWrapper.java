package com.yeapoo.odaesan.common.model;

import java.util.HashMap;
import java.util.Map;

public class DataWrapper {

    private int code;
    private String message;
    private Object data;

    public DataWrapper() {
        code = 200;
        message = "OK";
    }

    public DataWrapper(int id) {
        code = 200;
        message = "OK";
        Map<String, Integer> data = new HashMap<String, Integer>();
        data.put("id", id);
        this.data = data;
    }

    public DataWrapper(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public DataWrapper(Object data) {
        code = 200;
        message = "OK";
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
