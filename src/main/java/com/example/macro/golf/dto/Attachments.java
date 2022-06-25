package com.example.macro.golf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Attachments {
    @JsonProperty("fields")
    private List<Fields> fields;
    @JsonProperty("color")
    private String color;
    @JsonProperty("pretext")
    private String pretext;
    @JsonProperty("fallback")
    private String fallback;

    public List<Fields> getFields() {
        return fields;
    }

    public void setFields(List<Fields> fields) {
        this.fields = fields;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPretext() {
        return pretext;
    }

    public void setPretext(String pretext) {
        this.pretext = pretext;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }
}
