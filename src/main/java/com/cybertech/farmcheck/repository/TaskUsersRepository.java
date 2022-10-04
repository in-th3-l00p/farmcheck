package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.TaskUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUsersRepository extends JpaRepository<TaskUsers, Long> {
}
