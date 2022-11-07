package com.cybertech.farmcheck.domain;

import com.cybertech.farmcheck.service.dto.MessageDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Message")
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(
        name = "chat_id",
        nullable = false
    )
    private Chat chat;

    public Message() {
        this.dateTime = LocalDateTime.now();
    }

    public Message(MessageDTO messageDTO, Chat chat) {
        sender = messageDTO.getSender();
        text = messageDTO.getText();
        this.chat = chat;
        this.dateTime = LocalDateTime.now();
    }

    public Message(Long id, String sender, String text, Chat chat) {
        this.id = id;
        this.sender = sender;
        this.text = text;
        this.chat = chat;
        this.dateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
