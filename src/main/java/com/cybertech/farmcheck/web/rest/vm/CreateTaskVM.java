package com.cybertech.farmcheck.web.rest.vm;

import com.cybertech.farmcheck.service.dto.TaskDTO;

import java.util.List;

public class CreateTaskVM {

    private List<Long> userIds;

    private TaskDTO task;

    public CreateTaskVM() {
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }
}
