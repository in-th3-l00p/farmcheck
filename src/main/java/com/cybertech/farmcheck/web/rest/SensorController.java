package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Sensor;
import com.cybertech.farmcheck.domain.SensorData;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.SensorService;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.dto.SensorDTO;
import com.cybertech.farmcheck.service.dto.SensorDataDTO;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.web.rest.errors.SensorNotFoundException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    private final FarmService farmService;

    @Autowired
    public SensorController(
        SensorService sensorService,
        FarmService farmService
    ) {
        this.sensorService = sensorService;
        this.farmService = farmService;
    }

    /**
     * {@code GET /api/sensors} : gets a sensor
     * @param sensorId the sensor id
     * @return the sensor dto, with status {@code 200 (OK)}
     * @throws UnauthenticatedException with status {@code 401 (NOT AUTHORIZED)}
     * @throws SensorNotFoundException with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException with status {@code 401 (NOT AUTHORIZED)}
     */
    @GetMapping
    public SensorDTO getSensor(@RequestParam("sensorId") Long sensorId)
        throws
        UnauthenticatedException,
        SensorNotFoundException,
        UserDeniedAccessException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Sensor sensor = sensorService
            .getSensor(sensorId)
            .orElseThrow(() -> new SensorNotFoundException(sensorId));
        farmService.checkUserAccess(sensor.getFarm(), userLogin);

        return new SensorDTO(sensor);
    }

    /**
     * {@code GET /api/sensors/farm} : gets the farm of a sensor
     * @param sensorId the sensor's id
     * @return the farm dto, with status {@code 200 (OK)}
     * @throws UserDeniedAccessException with status {@code 401 (NOT AUTHORIZED)}
     * @throws SensorNotFoundException with status {@code 404 (NOT FOUND)}
     * @throws UnauthenticatedException with status {@code 401 (NOT AUTHORIZED)}
     */
    @GetMapping("/farm")
    public FarmDTO getSensorFarm(@RequestParam("sensorId") Long sensorId) throws
        UserDeniedAccessException,
        SensorNotFoundException,
        UnauthenticatedException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Sensor sensor = sensorService
            .getSensor(sensorId)
            .orElseThrow(() -> new SensorNotFoundException(sensorId));
        farmService.checkUserAccess(sensor.getFarm(), userLogin);

        return new FarmDTO(sensor.getFarm());
    }

    // todo improve security
    /**
     * {@code POST /api/sensors/data} : adds a {@link SensorData} entity to a sensor.
     * @param sensorToken the sensor's token
     * @param sensorDataDTO the data's dto
     * @return message, with status {@code 200 (OK)}
     * @throws SensorNotFoundException with status {@code 404 (NOT FOUND)}
     */
    @PostMapping("/data")
    public String sendSensorData(
        @RequestParam("sensorToken") String sensorToken,
        @RequestBody SensorDataDTO sensorDataDTO
    ) throws SensorNotFoundException {
        Optional<Sensor> optionalSensor = sensorService
            .getSensor(sensorToken);
        if (optionalSensor.isEmpty())
            throw new SensorNotFoundException(sensorToken);

        Sensor sensor = optionalSensor.get();
        SensorData sensorData = new SensorData(sensorDataDTO, sensor);
        sensorService.addSensorData(sensor, sensorData);
        return "Data received";
    }

    /**
     * {@code GET /api/sensors/data} : gets every {@link SensorData} of a sensor.
     * @param sensorId the sensor's id
     * @param page current page
     * @param size page's size
     * @return {@link ResponseEntity<>} the list of sensor data
     * @throws SensorNotFoundException with status {@code 404 (NOT FOUND)}
     */
    @GetMapping("/data")
    public List<SensorDataDTO> getSensorData(
        @RequestParam("sensorId") Long sensorId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size
    ) throws SensorNotFoundException {
        if (size > 10)
            return List.of();
        if (sensorService.getSensor(sensorId).isEmpty())
            throw new SensorNotFoundException(sensorId);

        return sensorService.getSensorData(
                sensorId, PageRequest.of(page, size, Sort.by("dateTime").descending())
            )
            .stream()
            .map(SensorDataDTO::new)
            .limit(10)
            .toList();
    }
}
