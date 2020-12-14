package com.ashish.frenzy.Model;

public class Chat {
    private String chatId;

    public Chat(String chatId, String contactName) {
        this.chatId = chatId;
        this.contactName = contactName;
    }

    public Chat(String chatId) {
        this.chatId = chatId;
    }

    private String contactName;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
