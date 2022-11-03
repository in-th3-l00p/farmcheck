package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.SensorData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorDataRepository extends PagingAndSortingRepository<SensorData, Long> {
}
