package com.cybertech.farmcheck.domain;

import com.cybertech.farmcheck.service.dto.ChatDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "Chat")
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @NotNull
    @Column
    private LocalDate addedDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(
        name = "farm_id",
        nullable = false
    )
    private Farm farm;

    @OneToMany(
        mappedBy = "chat",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Message> messages;

    public Chat() {

    }

    public Chat(String name, Farm farm) {
        this.name = name;
        this.farm = farm;
    }

    public Chat(ChatDTO chatDTO) {
        this.name = chatDTO.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
