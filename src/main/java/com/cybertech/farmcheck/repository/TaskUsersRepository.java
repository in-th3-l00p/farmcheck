package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Task;
import com.cybertech.farmcheck.domain.TaskUsers;
import com.cybertech.farmcheck.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskUsersRepository extends JpaRepository<TaskUsers, Long> {
    Optional<TaskUsers> findTaskUsersByTaskAndUser(Task task, User user);
}
