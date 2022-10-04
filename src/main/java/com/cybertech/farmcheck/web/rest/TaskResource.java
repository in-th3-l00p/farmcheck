package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.Task;
import com.cybertech.farmcheck.domain.TaskUsers;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.TaskService;
import com.cybertech.farmcheck.service.UserService;
import com.cybertech.farmcheck.service.dto.TaskDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.TaskNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import com.cybertech.farmcheck.web.rest.vm.CreateTaskVM;
import com.cybertech.farmcheck.web.rest.vm.TaskStatusVM;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/farms/tasks")
public class TaskResource {

    TaskService taskService;

    FarmService farmService;

    UserService userService;

    public TaskResource(
        TaskService taskService,
        FarmService farmService,
        UserService userService
    ) {
        this.taskService = taskService;
        this.farmService = farmService;
        this.userService = userService;
    }

    /**
     * {@code POST /api/tasks} : Creates a new task.
     * @param farmId the task's farm id
     * @param createTaskVM contains the list of user ids and the task dto.
     * @return message with status {@code 201 (CREATED)}
     * @throws UnauthenticatedException
     * @throws FarmNotFoundException
     * @throws UserDeniedAccessException if the user isn't an admin or an owner of the farm
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createTask(
        @RequestParam("farmId") Long farmId,
        @RequestBody CreateTaskVM createTaskVM
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException
    {
        User authenticatedUser = userService
            .getUserWithAuthorities()
            .orElseThrow(UnauthenticatedException::new);
        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, authenticatedUser.getLogin());
        if (userService.getFarmRole(authenticatedUser, farm.getId()) == 3)
            throw new UserDeniedAccessException(
                authenticatedUser.getLogin(), farm.getId()
            );
        taskService.addTask(
            farm,
            createTaskVM.getUserIds(),
            createTaskVM.getTask()
        );

        return "Task created";
    }

    /**
     * {@code GET : /api/tasks} : gets every task of the authenticated user.
     * @return the {@link List<TaskDTO>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the user is not authenticated
     */
    @GetMapping
    public List<TaskDTO> getTasks()
        throws UnauthenticatedException
    {
        User authenticatedUser = userService
            .getUserWithAuthorities()
            .orElseThrow(UnauthenticatedException::new);
        return taskService.getUserTasks(authenticatedUser.getId()).stream()
            .map(task -> {
                TaskUsers taskUsers = taskService
                    .getTaskUsers(task, authenticatedUser)
                    .get();
                return new TaskDTO(task, taskUsers.getStatus());
            })
            .toList();
    }

    /**
     * {@code GET /api/tasks/farm} : gets every task of a farm.
     * @param farmId farm's id
     * @return the {@link List<TaskDTO>}, with status {@code 200 (OK)}
     * @throws UnauthenticatedException
     * @throws FarmNotFoundException
     * @throws UserDeniedAccessException if the user isn't an admin or an owner of the farm
     */
    @GetMapping("/farm")
    public List<TaskDTO> getFarmTasks(
        @RequestParam("farmId") Long farmId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException
    {
        User authenticatedUser = userService
            .getUserWithAuthorities()
            .orElseThrow(UnauthenticatedException::new);
        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, authenticatedUser.getLogin());
        if (userService.getFarmRole(authenticatedUser, farm.getId()) == 3)
            throw new UserDeniedAccessException(
                authenticatedUser.getLogin(), farm.getId()
            );
        return farm.getTasks().stream().map(TaskDTO::new).toList();
    }

    /**
     * {@code GET /api/farms/tasks/status} : gets the status of every user for a task.
     * @param taskId the task's id
     * @return the statuses
     * @throws TaskNotFoundException if the task doesn't exist
     */
    @GetMapping("/status")
    public List<TaskStatusVM> getTaskStatus(
        @RequestParam("taskId") Long taskId
    ) throws TaskNotFoundException {
        Task task = taskService
            .getTaskById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        List<TaskStatusVM> status = new ArrayList<>();
        for (TaskUsers taskUsers: task.getUsers())
            status.add(new TaskStatusVM(
                taskUsers.getUser().getLogin(),
                taskUsers.getStatus()
            ));
        return status;
    }

    /**
     * {@code PUT /api/tasks} : updates a user's task as being finished.
     * @param taskId the task's id
     * @return message, with status {@code 200 (OK)}
     * @throws UnauthenticatedException
     * @throws TaskNotFoundException
     */
    @PutMapping
    public String finishTask(@Param("taskId") Long taskId) throws
        UnauthenticatedException,
        TaskNotFoundException
    {
        taskService.finishTask(taskId);
        return "Task updated.";
    }

    /**
     * {@code PUT /api/tasks/update} : updates a task
     * @param taskId the task's id
     * @param taskDTO dto containing the new data
     * @return message, with status {@code 200 (OK)}
     * @throws TaskNotFoundException
     * @throws UnauthenticatedException
     * @throws UserDeniedAccessException
     */
    @PutMapping("/update")
    public String updateTask(
        @RequestParam("taskId") Long taskId,
        @RequestBody TaskDTO taskDTO
    ) throws TaskNotFoundException, UnauthenticatedException, UserDeniedAccessException {
        Task task = taskService.getTaskById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));

        User authenticatedUser = userService
            .getUserWithAuthorities()
            .orElseThrow(UnauthenticatedException::new);
        farmService.checkUserAccess(task.getFarm(), authenticatedUser.getLogin());
        if (userService.getFarmRole(authenticatedUser, task.getFarm().getId()) == 3)
            throw new UserDeniedAccessException(
                authenticatedUser.getLogin(), task.getFarm().getId()
            );

        // updating fields
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setImportance(taskDTO.getImportance());
        task.setDeadline(taskDTO.getDeadline());
        taskService.updateTask(task);

        return "Task updated";
    }

    /**
     * {@code DELETE : /api/tasks} : deletes a task
     * @param taskId task's id
     * @return message, with status {@code 200 (OK)}, or status {@code 404 (NOT FOUND)}
     * @throws UnauthenticatedException
     * @throws UserDeniedAccessException
     */
    @DeleteMapping
    public String deleteTask(
        @RequestParam("taskId") Long taskId
    ) throws
        UnauthenticatedException,
        UserDeniedAccessException,
        TaskNotFoundException
    {
        Task task = taskService.getTaskById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));

        User authenticatedUser = userService
            .getUserWithAuthorities()
            .orElseThrow(UnauthenticatedException::new);
        farmService.checkUserAccess(task.getFarm(), authenticatedUser.getLogin());
        if (userService.getFarmRole(authenticatedUser, task.getFarm().getId()) == 3)
            throw new UserDeniedAccessException(
                authenticatedUser.getLogin(), task.getFarm().getId()
            );

        taskService.deleteTask(task);
        return "Task removed.";
    }
}
