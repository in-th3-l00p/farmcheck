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
    private Fields fields;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensors sensors;

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

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Sensors getSensors() {
        return sensors;
    }

    public void setSensors(Sensors sensors) {
        this.sensors = sensors;
    }

    @Override
    public String toString() {
        return "FieldSensors{" +
            "id=" + id +
            ", timestamp=" + timestamp +
            ", value=" + value +
            ", fields=" + fields +
            ", sensors=" + sensors +
            '}';
    }
}
