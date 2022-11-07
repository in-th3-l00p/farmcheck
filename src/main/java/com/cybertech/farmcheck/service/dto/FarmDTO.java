package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Farm;

public class FarmDTO {
    private Long id;

    private String name;

    private String description;

    private byte[] image;

    private Short role;

    public FarmDTO() {
    }

    public FarmDTO(Farm farm) {
        this.id = farm.getId();
        this.name = farm.getName();
        this.description = farm.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "FarmDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", role=" + role +
            '}';
    }
}
