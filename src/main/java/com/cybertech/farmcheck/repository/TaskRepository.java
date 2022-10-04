package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    @Query("SELECT t.task FROM TaskUsers AS t WHERE t.user.id = :id")
    List<Task> findAllByUserId(@Param("id") Long userId);
}
