package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.SensorData;

import java.time.LocalDateTime;

public class SensorDataDTO {

    private double soilHumidity;

    private double airHumidity;

    private double soilTemperature;

    private double airTemperature;

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

    public double getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(double soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public double getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(double airHumidity) {
        this.airHumidity = airHumidity;
    }

    public double getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(double soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(double airTemperature) {
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
