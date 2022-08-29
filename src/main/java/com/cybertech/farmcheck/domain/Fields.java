package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "fields")
public class Fields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "fields")
    private Set<Tasks> tasks;

    @OneToMany(mappedBy = "fields")
    private Set<FieldCorners> fieldCorners;

    @OneToMany(mappedBy = "fields")
    private Set<FieldSeeds> fieldSeeds;

    @OneToMany(mappedBy = "fields")
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

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
