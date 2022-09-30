package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Message;

import java.time.LocalDateTime;

// lombok not working wtf romania
public class MessageDTO {
    private String sender;
    private String text;
    private LocalDateTime datetime;

    public MessageDTO() {
    }

    public MessageDTO(String sender, String text, LocalDateTime datetime) {
        this.sender = sender;
        this.text = text;
        this.datetime = datetime;
    }

    public MessageDTO(Message message) {
        this.sender = message.getSender();
        this.text = message.getText();
        this.datetime = message.getDateTime();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "sender='" + sender + '\'' +
            ", text='" + text + '\'' +
            ", datetime='" + datetime + '\'' +
            '}';
    }
}
