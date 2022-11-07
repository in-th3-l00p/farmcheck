package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.SensorData;

import java.time.LocalDateTime;

public class SensorDataDTO {

    private Integer soilHumidity;

    private Integer airHumidity;

    private Integer soilTemperature;

    private Integer airTemperature;

    private LocalDateTime dateTime;

    public SensorDataDTO() {
    }

    public SensorDataDTO(SensorData sensorData) {
        soilHumidity = sensorData.getSoilHumidity();
        airHumidity = sensorData.getAirHumidity();
        soilTemperature = sensorData.getSoilTemperature();
        airTemperature = sensorData.getAirTemperature();
        dateTime = sensorData.getDateTime();
    }

    public Integer getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(Integer soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public Integer getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(Integer airHumidity) {
        this.airHumidity = airHumidity;
    }

    public Integer getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(Integer soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public Integer getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "SensorDataDTO{" +
            ", soilHumidity=" + soilHumidity +
            ", airHumidity=" + airHumidity +
            ", soilTemperature=" + soilTemperature +
            ", airTemperature=" + airTemperature +
            ", dateTime=" + dateTime +
            '}';
    }
}
