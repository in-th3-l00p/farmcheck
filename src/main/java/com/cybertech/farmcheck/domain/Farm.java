package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
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

    @Lob
    @Column
    private byte[] image;

    @OneToMany(
        mappedBy = "farm",
        fetch = FetchType.EAGER,
        cascade = CascadeType.REMOVE
    )
    private Set<FarmUsers> users;

    @OneToMany(mappedBy = "farm")
    private Set<Task> tasks;

    @OneToMany(
        mappedBy = "farm",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Message> messages;

    @OneToMany(mappedBy = "farm")
    private Set<Sensor> sensors;

    public Farm(String name, byte[] image) {
        this.name = name;
        this.image = image;
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

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
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
            ", image=" + Arrays.toString(image) +
            '}';
    }
}
