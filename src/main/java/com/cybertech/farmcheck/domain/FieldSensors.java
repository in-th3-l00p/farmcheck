package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "field_sensors")
public class FieldSensors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Time timestamp;

    @NotNull
    @Column
    private Double value;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    public Time getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Time timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Field getFields() {
        return field;
    }

    public void setFields(Field field) {
        this.field = field;
    }

    public Sensor getSensors() {
        return sensor;
    }

    public void setSensors(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "FieldSensors{" +
            "id=" + id +
            ", timestamp=" + timestamp +
            ", value=" + value +
            ", fields=" + field +
            ", sensors=" + sensor +
            '}';
    }
}
