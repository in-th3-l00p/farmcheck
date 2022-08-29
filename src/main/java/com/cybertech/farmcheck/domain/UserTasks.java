package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_tasks")
public class UserTasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTasks() {
        return task;
    }

    public void setTasks(Task task) {
        this.task = task;
    }
}
