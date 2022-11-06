package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.Sensor;
import com.cybertech.farmcheck.domain.SensorData;
import com.cybertech.farmcheck.repository.SensorDataRepository;
import com.cybertech.farmcheck.repository.SensorRepository;
import com.cybertech.farmcheck.security.sensor.SensorTokenGenerator;
import com.cybertech.farmcheck.service.dto.SensorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    private final SensorDataRepository sensorDataRepository;

    private final FarmService farmService;

    private final UserService userService;

    private final SensorTokenGenerator sensorTokenGenerator;

    @Autowired
    public SensorService(
        SensorRepository sensorRepository,
        SensorDataRepository sensorDataRepository,
        FarmService farmService,
        UserService userService,
        SensorTokenGenerator sensorTokenGenerator
    ) {
        this.sensorRepository = sensorRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.farmService = farmService;
        this.userService = userService;
        this.sensorTokenGenerator = sensorTokenGenerator;
    }

    /**
     * Adds a new sensor to a farm.
     *
     * @param farm      farm entity
     * @param sensorDTO the dto of the sensor
     */
    public void addSensor(Farm farm, SensorDTO sensorDTO) {
        Sensor sensor = new Sensor(sensorDTO);
        sensor.setToken(sensorTokenGenerator.generateUUID());
        sensor.setFarm(farm);
        sensorRepository.save(sensor);
    }

    /**
     * Adds a sensor data entity to a sensor.
     *
     * @param sensor     sensor entity
     * @param sensorData sensor data entity
     */
    public void addSensorData(Sensor sensor, SensorData sensorData) {
        sensorData.setSensor(sensor);
        sensor.getSensorDataSet().add(sensorData);
        sensorDataRepository.save(sensorData);
    }

    /**
     * Gets a sensor by token.
     *
     * @param sensorToken the sensor token
     * @return {@link Optional<Sensor>} containing the sensor entity
     */
    public Optional<Sensor> getSensor(String sensorToken) {
        return sensorRepository.findSensorByToken(sensorToken);
    }

    /**
     * Gets a sensor by id.
     *
     * @param sensorId the sensor's id
     * @return {@link Optional<Sensor>} containing the sensor entity
     */
    public Optional<Sensor> getSensor(Long sensorId) {
        return sensorRepository.findSensorById(sensorId);
    }

    /**
     * Gets every data entity of a sensor.
     *
     * @param sensorId the sensor's id
     * @return list of data entities
     */
    public List<SensorData> getSensorData(Long sensorId) {
        return sensorDataRepository.findAllSensorDataById(sensorId);
    }

    /**
     * Deletes a sensor from the db.
     *
     * @param sensor the given sensor
     */
    public void deleteSensor(Sensor sensor) {
        sensorRepository.delete(sensor);
    }
}
