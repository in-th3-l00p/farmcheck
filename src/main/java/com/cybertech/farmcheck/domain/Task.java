package com.cybertech.farmcheck.domain;

import com.cybertech.farmcheck.service.dto.TaskDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @Column
    private String description;

    @NotNull
    @Column
    private Boolean importance;

    @Column
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private Set<TaskUsers> users;

    public Task() {
    }

    public Task(
        String title,
        String description,
        Boolean importance,
        LocalDateTime deadline
    ) {
        this.title = title;
        this.description = description;
        this.importance = importance;
        this.deadline = deadline;
    }

    public Task(TaskDTO taskDTO) {
        this.title = taskDTO.getTitle();
        this.description = taskDTO.getDescription();
        this.importance = taskDTO.getImportance();
        this.deadline = taskDTO.getDeadline();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getImportance() {
        return importance;
    }

    public void setImportance(Boolean importance) {
        this.importance = importance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Set<TaskUsers> getUsers() {
        return users;
    }

    public void setUsers(Set<TaskUsers> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", importance=" + importance +
            ", deadline=" + deadline +
            ", farm=" + farm +
            '}';
    }

}
