package com.cybertech.farmcheck.domain;

import javax.persistence.*;

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

    public Message() {
    }

    public Message(Long id, String sender, String text) {
        this.id = id;
        this.sender = sender;
        this.text = text;
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
}
