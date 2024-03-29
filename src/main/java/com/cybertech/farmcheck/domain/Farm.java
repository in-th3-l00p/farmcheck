package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A farm.
 */
@Entity
@Table(name = "farms")
public class Farm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column
    private byte[] image;

    @OneToMany(
        mappedBy = "farm",
        fetch = FetchType.EAGER,
        cascade = CascadeType.REMOVE
    )
    private Set<FarmUsers> users;

    @OneToMany(
        mappedBy = "farm",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Task> tasks;

    @OneToMany(
        mappedBy = "farm",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Chat> chats;

    @OneToMany(
        mappedBy = "farm",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private Set<Sensor> sensors;

    public Farm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Farm() {
    }

    public Long getId() {
        return id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<FarmUsers> getUsers() {
        return users;
    }

    public void setUsers(Set<FarmUsers> users) {
        this.users = users;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Farm)) {
            return false;
        }
        return id != null && id.equals(((Farm) o).id);
    }

    @Override
    public String toString() {
        return "Farm{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
