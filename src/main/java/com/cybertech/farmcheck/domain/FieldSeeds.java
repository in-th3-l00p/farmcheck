package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "field_seeds")
public class FieldSeeds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Date planting_date;

    @NotNull
    @Column
    private Date harvest_date;

    @NotNull
    @Column
    private Short rating;

    @NotNull
    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "seed_id")
    private Seed seed;

    public Date getPlanting_date() {
        return planting_date;
    }

    public void setPlanting_date(Date planting_date) {
        this.planting_date = planting_date;
    }

    public Date getHarvest_date() {
        return harvest_date;
    }

    public void setHarvest_date(Date harvest_date) {
        this.harvest_date = harvest_date;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Field getFields() {
        return field;
    }

    public void setFields(Field field) {
        this.field = field;
    }

    public Seed getSeeds() {
        return seed;
    }

    public void setSeeds(Seed seed) {
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "FieldSeeds{" +
            "id=" + id +
            ", planting_date=" + planting_date +
            ", harvest_date=" + harvest_date +
            ", rating=" + rating +
            ", notes='" + notes + '\'' +
            ", fields=" + field +
            ", seeds=" + seed +
            '}';
    }
}
