package com.cybertech.farmcheck.security.sensor;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SensorTokenGenerator {
    public SensorTokenGenerator() {
    }

    /**
     * Generates a random UUID
     * @return the uuid as a string
     */
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
