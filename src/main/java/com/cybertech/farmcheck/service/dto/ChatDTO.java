package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Chat;

// lombok not working wtf romania
public class ChatDTO {
    private Long id;
    private String name;

    public ChatDTO() {
    }

    public ChatDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.name = chat.getName();
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

    @Override
    public String toString() {
        return "ChatDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
