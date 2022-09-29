package com.cybertech.farmcheck.domain;

import com.cybertech.farmcheck.service.dto.SensorDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "sensors")
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String token;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column(columnDefinition = "text")
    private String description;

    @NotNull
    @Column
    private LocalDate addedDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(
        mappedBy = "sensor",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private Set<SensorData> sensorDataSet;

    public Sensor() {
    }

    public Sensor(SensorDTO sensorDTO) {
        name = sensorDTO.getName();
        description = sensorDTO.getDescription();
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

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Set<SensorData> getSensorDataSet() {
        return sensorDataSet;
    }

    public void setSensorDataSet(Set<SensorData> sensorDataSet) {
        this.sensorDataSet = sensorDataSet;
    }

    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", addedDate=" + addedDate +
            ", farm=" + farm +
            '}';
    }
}
