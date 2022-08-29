package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "fields")
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "field")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "field")
    private Set<FieldCorners> fieldCorners;

    @OneToMany(mappedBy = "field")
    private Set<FieldSeeds> fieldSeeds;

    @OneToMany(mappedBy = "field")
    private Set<FieldSensors> fieldSensors;

    public Set<FieldSensors> getFieldSensors() {
        return fieldSensors;
    }

    public void setFieldSensors(Set<FieldSensors> fieldSensors) {
        this.fieldSensors = fieldSensors;
    }

    public Set<FieldSeeds> getFieldSeeds() {
        return fieldSeeds;
    }

    public void setFieldSeeds(Set<FieldSeeds> fieldSeeds) {
        this.fieldSeeds = fieldSeeds;
    }

    public Set<FieldCorners> getFieldCorners() {
        return fieldCorners;
    }

    public void setFieldCorners(Set<FieldCorners> fieldCorners) {
        this.fieldCorners = fieldCorners;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
