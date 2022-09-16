package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Farm;

public class FarmDTO {
    private String name;
    private byte[] image;

    public FarmDTO(Farm farm){
        this.name = farm.getName();
        this.image = farm.getImage();
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
