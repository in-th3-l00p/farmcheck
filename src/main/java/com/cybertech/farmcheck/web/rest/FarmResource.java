package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.FarmUsers;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.dto.UserDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UnauthenticatedException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmResource {
    private final FarmService farmService;
    private final ResponseEntity<String> userNotInFarm = ResponseEntity
        .badRequest()
        .body("User doesn't belong to the farm.");

    @Autowired
    public FarmResource(FarmService farmService) {
        this.farmService = farmService;
    }

    /**
     * {@code GET /api/farms} : get all farms of a user.
     * @return the {@link List<FarmDTO>} made of user's farms, with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws UserNotFoundException if the authenticated client's account doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @GetMapping
    public List<FarmDTO> getUserFarms()
        throws UnauthenticatedException, UserNotFoundException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        return farmService.getUserFarms(userLogin);
    }

    /**
     * {@code GET /api/farms/data} : get a farm
     * @param farmName the name of the farm
     * @return {@link ResponseEntity<FarmDTO>} with status {@code 200 {OK}}, or status {@code 400 (BAD_REQUEST)}
     */
    @GetMapping("/data")
    public ResponseEntity<?> getFarm(
        @RequestParam("farmName") String farmName
    ) throws UnauthenticatedException, FarmNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarmByName(farmName);
        if (farmService.isUserInFarm(farm, userLogin))
            return ResponseEntity.ok(new FarmDTO(farm));
        return userNotInFarm;
    }

    /**
     * {@code GET /api/farms/users} : get the users of a farm
     * @param farmName the name of the farm
     * @return the {@link ResponseEntity<List<UserDTO>>} of the farm's users with {@code 200 (OK)}, or status {@code 400 (BAD_REQUEST)}
     * @throws UnauthenticatedException with status {@code 401 (UNAUTHORIZED)}
     * @throws FarmNotFoundException if the given farm doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @GetMapping("/users")
    public ResponseEntity<?> getFarmUsers(
        @RequestParam("farmName") String farmName
    ) throws UnauthenticatedException, FarmNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarmByName(farmName);
        if (farmService.isUserInFarm(farm, userLogin))
            return ResponseEntity.ok(
                farm.getUsers().stream()
                    .map(FarmUsers::getUser)
                    .map(UserDTO::new)
                    .toList()
            );

        return userNotInFarm;
    }

    /**
     * {@code POST /api/farms} : create a farm record
     * @param farmDTO the farm's information
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws UserNotFoundException if the user doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @PostMapping
    public ResponseEntity<String> createFarm(@RequestBody FarmDTO farmDTO)
        throws UnauthenticatedException, UserNotFoundException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        farmService.create(farmDTO, userLogin);
        return ResponseEntity.ok("Farm created.");
    }

    /**
     * {@code PUT /api/farms/update} : updates a farm record
     * @param farmName the updated farm's name
     * @param farmDTO new farm fields
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateFarm(
        @RequestParam("farmName") String farmName,
        @RequestBody FarmDTO farmDTO
    ) throws UnauthenticatedException, FarmNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarmByName(farmName);
        if (!farmService.isUserInFarm(farm, userLogin))
            return userNotInFarm;

        // todo add privilege checking

        farmService.update(farm, farmDTO);
        return ResponseEntity.ok("Farm updated.");
    }


    /**
     * {@code DELETE /api/farms/delete} : deletes a farm record
     * @param farmName the deleted farm's name
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFarm(
        @RequestParam("farmName") String farmName
    ) throws UnauthenticatedException, FarmNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarmByName(farmName);
        if (!farmService.isUserInFarm(farm, userLogin))
            return userNotInFarm;

        farmService.delete(farm);
        return ResponseEntity.ok("Farm deleted.");
    }
}
