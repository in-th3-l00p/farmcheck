package com.cybertech.farmcheck.domain;

import com.cybertech.farmcheck.service.dto.SensorDataDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    LocalDateTime dateTime = LocalDateTime.now();

    @Column
    private Integer soilHumidity;

    @Column
    private Integer airHumidity;

    @Column
    private Integer soilTemperature;

    @Column
    private Integer airTemperature;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public SensorData() {
    }

    public SensorData(
        SensorDataDTO sensorDataDTO,
        Sensor sensor
    ) {
        soilHumidity = sensorDataDTO.getSoilHumidity();
        airHumidity = sensorDataDTO.getAirHumidity();
        soilTemperature = sensorDataDTO.getSoilTemperature();
        airTemperature = sensorDataDTO.getAirTemperature();
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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "SensorData{" +
            "id=" + id +
            ", dateTime=" + dateTime +
            ", soilHumidity=" + soilHumidity +
            ", airHumidity=" + airHumidity +
            ", soilTemperature=" + soilTemperature +
            ", airTemperature=" + airTemperature +
            ", sensor=" + sensor +
            '}';
    }
}
