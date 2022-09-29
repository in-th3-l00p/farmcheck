package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Sensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SensorRepository extends PagingAndSortingRepository<Sensor, Long> {
    @Query("SELECT t FROM Sensor AS t WHERE t.token = :token")
    Optional<Sensor> findSensorByToken(@Param("token") String sensorToken);

    Optional<Sensor> findSensorById(@Param("sensor_id") Long sensorId);
}
