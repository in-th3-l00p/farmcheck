package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Sensor;

public class SensorDTO {
    private Long id;

    private String token;

    private String name;

    private String description;

    public SensorDTO() {
    }

    public SensorDTO(Sensor sensor) {
        id = sensor.getId();
        token = sensor.getToken();
        name = sensor.getName();
        description = sensor.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "SensorDTO{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
