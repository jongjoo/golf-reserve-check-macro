package com.example.macro.golf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SlackMsgDTO {

    @JsonProperty("attachments")
    private List<Attachments> attachments;

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
}
