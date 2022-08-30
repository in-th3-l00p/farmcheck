package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmResource {
    @Autowired
    private FarmService farmService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllFarms() {
        List<Farm> farms = farmService.findAll();
        return ResponseEntity.ok(farms);
    }

    @GetMapping("/create")
    public ResponseEntity<?> createFarm(@RequestBody FarmDTO farmDTO){
        try{
            String userLogin = SecurityUtils.getCurrentUserLogin().get();
            farmService.create(farmDTO,userLogin);
            return ResponseEntity.ok("Farm created successfully!");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error during creation of the farm");
        }
    }
}
