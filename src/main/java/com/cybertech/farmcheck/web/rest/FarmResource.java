package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.Sensor;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.SensorService;
import com.cybertech.farmcheck.service.UserService;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.dto.FarmUserDTO;
import com.cybertech.farmcheck.service.dto.SensorDTO;
import com.cybertech.farmcheck.service.dto.UserDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import com.cybertech.farmcheck.web.rest.errors.SensorNotFoundException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import com.cybertech.farmcheck.web.rest.vm.FarmUserRoleVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmResource {
    private final FarmService farmService;

    private final UserService userService;

    private final SensorService sensorService;

    @Autowired
    public FarmResource(
        FarmService farmService,
        UserService userService,
        SensorService sensorService
    ) {
        this.farmService = farmService;
        this.userService = userService;
        this.sensorService = sensorService;
    }

    /**
     * {@code GET /api/farms} : get all farms of a user.
     *
     * @return the {@link List<FarmDTO>} made of user's farms, with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws UserNotFoundException    if the authenticated client's account doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @GetMapping
    public List<FarmDTO> getUserFarms()
        throws UnauthenticatedException, UserNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        return farmService.getUserFarms(userLogin);
    }

    /**
     * {@code GET /api/farms/data} : get a farm
     *
     * @param farmId farm's id
     * @return {@link ResponseEntity<FarmDTO>} with status {@code 200 {OK}}, or status {@code 400 (BAD_REQUEST)}
     * @throws UserDeniedAccessException if the user doesn't have access to the farm, with status {@code 401 (NOT_AUTHORIZED)}
     */
    @GetMapping("/data")
    public FarmDTO getFarm(
        @RequestParam("farmId") Long farmId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        User user = userService
            .getUserWithAuthoritiesByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);
        FarmDTO farmDTO = new FarmDTO(farm);
        farmDTO.setRole(userService.getFarmRole(user, farmId));
        return farmDTO;
    }


    /**
     * {@code GET /api/farms/users} : get the users of a farm
     *
     * @param farmId farm's id
     * @return the {@link ResponseEntity<List<UserDTO>>} of the farm's users with {@code 200 (OK)}, or status {@code 400 (BAD_REQUEST)}
     * @throws UnauthenticatedException  with status {@code 401 (UNAUTHORIZED)}
     * @throws FarmNotFoundException     if the given farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException if the user doesn't have access to the farm, with status {@code 401 (NOT_AUTHORIZED)}
     */
    @GetMapping("/users")
    public List<FarmUserDTO> getFarmUsers(
        @RequestParam("farmId") Long farmId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);
        return farm.getUsers()
            .stream()
            .map((farmUsers) -> new FarmUserDTO(
                farmUsers.getUser(),
                farmUsers.getRole())
            )
            .toList();
    }

    /**
     * {@code GET /api/farms/sensor} : get farm's sensors.
     *
     * @param farmId the farm's id
     * @return the list of sensors
     * @throws UnauthenticatedException  with status {@code 401 (UNAUTHORIZED)}
     * @throws FarmNotFoundException     if the given farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException if the user doesn't have access to the farm, with status {@code 401 (NOT_AUTHORIZED)}
     */
    @GetMapping("/sensor")
    public List<SensorDTO> getFarmSenors(
        @RequestParam("farmId") Long farmId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        return farm.getSensors()
            .stream()
            .map(SensorDTO::new)
            .toList();
    }

    /**
     * {@code POST /api/farms} : create a farm record
     *
     * @param farmDTO the farm's information
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws UserNotFoundException    if the user doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @PostMapping
    public ResponseEntity<String> createFarm(@RequestBody FarmDTO farmDTO)
        throws UnauthenticatedException, UserNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        farmService.create(farmDTO, userLogin);
        return ResponseEntity.ok("Farm created.");
    }

    /**
     * {@code POST /api/farms/sensor} : used for adding a new sensor to a farm.
     *
     * @param farmId    the farm's id
     * @param sensorDTO the sensor data transfer object
     * @return message status, with status {@code 201 (CREATED)}
     * @throws UnauthenticatedException  with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException     with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException with status {@code 401 (NOT AUTHORIZED)}
     */
    @PostMapping("/sensor")
    @ResponseStatus(HttpStatus.CREATED)
    public String addSensor(
        @RequestParam("farmId") Long farmId,
        @RequestBody SensorDTO sensorDTO
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);
        sensorService.addSensor(farm, sensorDTO);

        return "Sensor added";
    }

    /**
     * {@code PUT /api/farms/update} : updates a farm record
     *
     * @param farmId  farm's id
     * @param farmDTO new farm fields
     * @return {@code String} with status {@code 200 (OK)}
     * @throws UnauthenticatedException  if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException     if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException if the user doesn't have access to the farm, with status {@code 401 (NOT_AUTHORIZED)}
     * @throws UserNotFoundException     if the user doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @PutMapping("/update")
    public String updateFarm(
        @RequestParam("farmId") Long farmId,
        @RequestBody FarmDTO farmDTO
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        // role checking
        User user = userService
            .getUserWithAuthoritiesByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));
        if (userService.getFarmRole(user, farmId) == 3)
            throw new UserDeniedAccessException(userLogin, farmId);

        farmService.update(farm, farmDTO);
        return "Farm updated.";
    }

    /**
     * {@code PUT /api/farms/roles} : Changes the role of a user.
     *
     * @param body request body used for getting farm id, user login and the new role
     * @return status message
     * @throws UnauthenticatedException  with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException     with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException with status {@code 401 (NOT AUTHORIZED)}
     */
    @PutMapping("/roles")
    public ResponseEntity<String> changeUserRole(@RequestBody FarmUserRoleVM body)
        throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String authenticatedUserLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(body.getFarmId());
        farmService.checkUserAccess(farm, authenticatedUserLogin);

        User user = userService
            .getUserWithAuthoritiesByLogin(body.getUserLogin())
            .orElseThrow(() -> new UserNotFoundException(body.getUserLogin()));
        User authenticatedUser = userService
            .getUserWithAuthoritiesByLogin(authenticatedUserLogin)
            .orElseThrow(() -> new UserNotFoundException(authenticatedUserLogin));
        if (userService.getFarmRole(authenticatedUser, farm.getId()) == 3)
            throw new UserDeniedAccessException(authenticatedUserLogin, farm.getId());

        userService.changeUserFarmRole(user, farm.getId(), body.getRole());
        return ResponseEntity.ok("Role updated");
    }

    /**
     * {@code DELETE /api/farms/delete} : deletes a farm record
     *
     * @param farmId the deleted farm's id
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException  if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException     if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException if the user doesn't have access to the farm, with status {@code 401 (NOT_AUTHORIZED)}
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFarm(
        @RequestParam("farmId") Long farmId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        User user = userService
            .getUserWithAuthoritiesByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);
        if (userService.getFarmRole(user, farmId) != 1)
            throw new UserDeniedAccessException(userLogin, farmId);

        farmService.delete(farm);
        return ResponseEntity.ok("Farm deleted.");
    }

    /**
     * {@code DELETE /api/farms/sensor} : deletes a sensor from a farm.
     *
     * @param farmId   the farm's id
     * @param sensorId the sensor's id
     * @return message status with status {@code 200 (OK)}
     * @throws UnauthenticatedException  with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException     with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException with status {@code 401 (NOT AUTHORIZED)}
     * @throws SensorNotFoundException   with status {@code 404 (NOT FOUND)}
     */
    @DeleteMapping("/sensor")
    public ResponseEntity<String> deleteFarmSensor(
        @RequestParam("farmId") Long farmId,
        @RequestParam("sensorId") Long sensorId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException,
        SensorNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        Sensor sensor = sensorService
            .getSensor(sensorId)
            .orElseThrow(() -> new SensorNotFoundException(sensorId));
        sensorService.deleteSensor(sensor);
        return ResponseEntity.ok("Sensor deleted");
    }
}
