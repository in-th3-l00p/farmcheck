package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
}
