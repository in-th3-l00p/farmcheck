package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.Task;
import com.cybertech.farmcheck.domain.TaskUsers;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.repository.TaskRepository;
import com.cybertech.farmcheck.repository.TaskUsersRepository;
import com.cybertech.farmcheck.service.dto.TaskDTO;
import com.cybertech.farmcheck.service.exception.TaskNotFoundException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    private UserService userService;

    private TaskUsersRepository taskUsersRepository;

    @Autowired
    public TaskService(
        TaskRepository taskRepository,
        UserService userService,
        TaskUsersRepository taskUsersRepository
    ) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskUsersRepository = taskUsersRepository;
    }

    /**
     * Adds a task to a farm.
     * @param farm the farm entity
     * @param usersId the users that have this task
     * @param taskDTO the task data transfer object
     * @return the task entity
     */
    public Task addTask(Farm farm, List<Long> usersId, TaskDTO taskDTO) {
        Task task = new Task(taskDTO);
        task.setFarm(farm);
        task = taskRepository.save(task);
        for (Long userId : usersId) {
            User user = userService
                .getUserWithAuthoritiesById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
            TaskUsers taskUsers = new TaskUsers(task, user, false);
            taskUsersRepository.save(taskUsers);
        };

        return task;
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    public void finishTask(Long taskId) throws UnauthenticatedException, TaskNotFoundException {
        User authenticatedUser = userService
            .getUserWithAuthorities()
            .orElseThrow(UnauthenticatedException::new);
        TaskUsers taskUsers = authenticatedUser.getTasks().stream()
            .filter((potentialTaskUsers) -> Objects.equals(
                potentialTaskUsers.getTask().getId(), taskId)
            )
            .findFirst()
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        taskUsers.setStatus(true);
    }

    /**
     * Removes the given task from the db.
     * @param task the task entity
     */
    public void deleteTask(Task task) {
        for (TaskUsers farmUsers : task.getUsers())
            taskUsersRepository.delete(farmUsers);
        taskRepository.delete(task);
    }
}
