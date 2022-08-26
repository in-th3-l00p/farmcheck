package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "sensors")
public class Sensors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Short type;

    @NotNull
    @Column
    private Double latitude;

    @NotNull
    @Column
    private Double longitude;

    @OneToMany(mappedBy = "sensors")
    private Set<FieldSensors> fieldSensors;

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<FieldSensors> getFieldSensors() {
        return fieldSensors;
    }

    public void setFieldSensors(Set<FieldSensors> fieldSensors) {
        this.fieldSensors = fieldSensors;
    }

    @Override
    public String toString() {
        return "Sensors{" +
            "id=" + id +
            ", type=" + type +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", fieldSensors=" + fieldSensors +
            '}';
    }
}
