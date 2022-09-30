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
        name = "farm_id",
        nullable = false
    )
    private Farm farm;

    public Message() {
        this.dateTime = LocalDateTime.now();
    }

    public Message(MessageDTO messageDTO, Farm farm) {
        sender = messageDTO.getSender();
        text = messageDTO.getText();
        this.farm = farm;
        this.dateTime = LocalDateTime.now();
    }

    public Message(Long id, String sender, String text, Farm farm) {
        this.id = id;
        this.sender = sender;
        this.text = text;
        this.farm = farm;
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

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
