package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "field_corners")
public class FieldCorners implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Double latitude;

    @NotNull
    @Column
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

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

    public Field getFields() {
        return field;
    }

    public void setFields(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "FieldCorners{" +
            "id=" + id +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", fields=" + field +
            '}';
    }

}
