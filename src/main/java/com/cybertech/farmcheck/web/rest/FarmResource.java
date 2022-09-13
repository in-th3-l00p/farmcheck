package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.service.exception.UnauthenticatedException;
import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmResource {
    private final FarmService farmService;

    @Autowired
    public FarmResource(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping
    public ResponseEntity<List<Farm>> getUserFarms()
        throws UnauthenticatedException, UserNotFoundException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        return ResponseEntity.ok(
            farmService.getUserFarms(userLogin)
        );
    }

    @PostMapping
    public void createFarm(@RequestBody FarmDTO farmDTO)
        throws UnauthenticatedException, UserNotFoundException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        farmService.create(farmDTO, userLogin);
    }
}
