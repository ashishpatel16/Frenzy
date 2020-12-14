package com.ashish.frenzy.Model;

import java.util.List;

public class Message {

    private String senderId, messageId, text;
    private List<String> mediaList;

    public Message(String senderId, String messageId, String text, List<String> mediaList) {
        this.senderId = senderId;
        this.messageId = messageId;
        this.text = text;
        this.mediaList = mediaList;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<String> mediaList) {
        this.mediaList = mediaList;
    }
}
