package com.ashish.frenzy.Model;

public class Message {

    private String senderId, messageId, text;

    public Message(String senderId, String messageId, String text) {
        this.senderId = senderId;
        this.messageId = messageId;
        this.text = text;
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
}
