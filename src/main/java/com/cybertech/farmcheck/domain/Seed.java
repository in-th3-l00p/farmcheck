package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "seeds")
public class Seed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(length = 50, nullable = false)
    private String name;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    private Integer time_to_grow;

    @NotNull
    @Column
    private String months_for_planting;

    @NotNull
    @Column
    private Integer seed_per_m2;

    @OneToMany(mappedBy = "seed")
    private Set<FieldSeeds> fieldSeeds;

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

    public Integer getTime_to_grow() {
        return time_to_grow;
    }

    public void setTime_to_grow(Integer time_to_grow) {
        this.time_to_grow = time_to_grow;
    }

    public String getMonths_for_planting() {
        return months_for_planting;
    }

    public void setMonths_for_planting(String months_for_planting) {
        this.months_for_planting = months_for_planting;
    }

    public Integer getSeed_per_m2() {
        return seed_per_m2;
    }

    public void setSeed_per_m2(Integer seed_per_m2) {
        this.seed_per_m2 = seed_per_m2;
    }

    public Set<FieldSeeds> getFieldSeeds() {
        return fieldSeeds;
    }

    public void setFieldSeeds(Set<FieldSeeds> fieldSeeds) {
        this.fieldSeeds = fieldSeeds;
    }

    @Override
    public String toString() {
        return "Seeds{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", time_to_grow=" + time_to_grow +
            ", months_for_planting='" + months_for_planting + '\'' +
            ", seed_per_m2=" + seed_per_m2 +
            ", fieldSeeds=" + fieldSeeds +
            '}';
    }
}
