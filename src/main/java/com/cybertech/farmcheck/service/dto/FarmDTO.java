package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Farm;

public class FarmDTO {
    private Long id;
    private String name;
    private byte[] image;

    public FarmDTO() {
    }

    public FarmDTO(Farm farm){
        this.id = farm.getId();
        this.name = farm.getName();
        this.image = farm.getImage();
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
