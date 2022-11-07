package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.SensorData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SensorDataRepository extends PagingAndSortingRepository<SensorData, Long> {
}
