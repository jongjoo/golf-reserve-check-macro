package com.example.macro.golf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fields {
    @JsonProperty("short")
    private boolean short1;
    @JsonProperty("value")
    private String value;
    @JsonProperty("title")
    private String title;

    public boolean isShort1() {
        return short1;
    }

    public void setShort1(boolean short1) {
        this.short1 = short1;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
