package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Sensor;
import com.cybertech.farmcheck.domain.SensorData;
import com.cybertech.farmcheck.service.SensorService;
import com.cybertech.farmcheck.service.dto.SensorDataDTO;
import com.cybertech.farmcheck.web.rest.errors.SensorNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(
        SensorService sensorService
    ) {
        this.sensorService = sensorService;
    }

    /**
     * {@code POST /api/sensors/data} : adds a {@link SensorData} entity to a sensor.
     * @param sensorToken the sensor's token
     * @param sensorDataDTO the data's dto
     * @return message, with status {@code 200 (OK)}
     * @throws SensorNotFoundException with status {@code 404 (NOT FOUND)}
     */
    @PostMapping("/data")
    public ResponseEntity<String> sendSensorData(
        @Param("sensorToken") String sensorToken,
        @RequestBody SensorDataDTO sensorDataDTO
    ) throws SensorNotFoundException {
        Optional<Sensor> optionalSensor = sensorService
            .getSensor(sensorToken);
        if (optionalSensor.isEmpty())
            throw new SensorNotFoundException(sensorToken);

        Sensor sensor = optionalSensor.get();
        SensorData sensorData = new SensorData(sensorDataDTO, sensor);
        sensorService.addSensorData(sensor, sensorData);
        return ResponseEntity.ok("Data received");
    }

    /**
     * {@code GET /api/sensors/data} : gets every {@link SensorData} of a sensor.
     * @param sensorToken the sensor's token
     * @return {@link ResponseEntity<>} the list of sensor data
     * @throws SensorNotFoundException with status {@code 404 (NOT FOUND)}
     */
    @GetMapping("/data")
    public ResponseEntity<?> getSensorData(
        @Param("sensorToken") String sensorToken
    ) throws SensorNotFoundException {
        if (sensorService.getSensor(sensorToken).isEmpty())
            throw new SensorNotFoundException(sensorToken);

        return ResponseEntity.ok(
            sensorService.getSensorData(sensorToken)
        );
    }
}
