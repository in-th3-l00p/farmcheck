package com.cybertech.farmcheck.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @OneToMany(mappedBy = "farm")
    private Set<FarmUsers> users;

    @OneToMany(mappedBy = "farm")
    private Set<Order> orders;

    @OneToMany(mappedBy = "farm")
    private Set<Tasks> tasks;

    @OneToMany(mappedBy = "farm")
    private Set<Fields> fields;

    public Set<Fields> getFields() {
        return fields;
    }

    public void setFields(Set<Fields> fields) {
        this.fields = fields;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
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
