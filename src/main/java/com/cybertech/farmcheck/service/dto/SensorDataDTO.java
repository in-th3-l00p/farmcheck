package com.cybertech.farmcheck.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SensorDataDTO {

    private double soilHumidity;

    private double airHumidity;

    private double soilTemperature;

    private double airTemperature;

    private LocalDateTime localDateTime;

    public SensorDataDTO() {
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "SensorDataDTO{" +
            ", soilHumidity=" + soilHumidity +
            ", airHumidity=" + airHumidity +
            ", soilTemperature=" + soilTemperature +
            ", airTemperature=" + airTemperature +
            ", localDateTime=" + localDateTime +
            '}';
    }
}
