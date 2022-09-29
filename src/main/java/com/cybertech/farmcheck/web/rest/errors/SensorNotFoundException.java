package com.cybertech.farmcheck.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SensorNotFoundException extends Exception {
    public SensorNotFoundException(String sensorToken) {
        super(String.format("Sensor %s not found.", sensorToken));
    }

    public SensorNotFoundException(Long sensorId) {
        super(String.format("Sensor %d not found.", sensorId));
    }
}
