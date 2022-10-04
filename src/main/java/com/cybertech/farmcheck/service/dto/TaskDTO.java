package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Task;
import com.cybertech.farmcheck.domain.TaskUsers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean status;
    private Boolean importance;
    private LocalDateTime creationDate;
    private LocalDateTime deadline;
    private List<Long> userIds = new ArrayList<>();

    public TaskDTO() {
    }

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.importance = task.getImportance();
        this.creationDate = task.getCreationDate();
        this.deadline = task.getDeadline();
        for (TaskUsers taskUsers: task.getUsers())
            userIds.add(taskUsers.getUser().getId());
    }

    public TaskDTO(TaskUsers taskUsers) {
        this.id = taskUsers.getTask().getId();
        this.title = taskUsers.getTask().getTitle();
        this.description = taskUsers.getTask().getDescription();
        this.status = taskUsers.getStatus();
        this.importance = taskUsers.getTask().getImportance();
        this.creationDate = taskUsers.getTask().getCreationDate();
        this.deadline = taskUsers.getTask().getDeadline();
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", status=" + status +
            ", importance=" + importance +
            ", creationDate=" + creationDate +
            ", deadline=" + deadline +
            '}';
    }
}
