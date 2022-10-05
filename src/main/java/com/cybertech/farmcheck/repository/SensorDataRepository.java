package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.SensorData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorDataRepository extends PagingAndSortingRepository<SensorData, Long> {
    @Query("SELECT d FROM SensorData AS d WHERE d.sensor.id = :sensor_id ORDER BY d.dateTime")
    List<SensorData> findAllSensorDataById(@Param("sensor_id") Long sensorId);
}
