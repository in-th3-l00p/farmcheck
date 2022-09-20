package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.FarmUsers;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.dto.UserDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserAccessDeniedException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
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
     * @param farmId farm's id
     * @return {@link ResponseEntity<FarmDTO>} with status {@code 200 {OK}}
     * @throws UnauthenticatedException if the user isn't authenticated, with status {@code 401 (UNAUTHORIZED)}
     * @throws UserAccessDeniedException if the user isn't authenticated, with status {@code 401 (UNAUTHORIZED)}
     * @throws FarmNotFoundException if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @GetMapping("/data")
    public FarmDTO getFarm(
        @RequestParam("farmId") Long farmId
    ) throws UnauthenticatedException, UserAccessDeniedException, FarmNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);
        return new FarmDTO(farm);
    }

    /**
     * {@code GET /api/farms/users} : get the users of a farm
     * @param farmId farm's id
     * @return the {@link ResponseEntity<List<UserDTO>>} of the farm's users with {@code 200 (OK)}
     * @throws UnauthenticatedException with status {@code 401 (UNAUTHORIZED)}
     * @throws FarmNotFoundException if the given farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserAccessDeniedException if the user isn't authenticated, with status {@code 401 (UNAUTHORIZED)}
     */
    @GetMapping("/users")
    public List<UserDTO> getFarmUsers(
        @RequestParam("farmId") Long farmId
    ) throws UnauthenticatedException, FarmNotFoundException, UserAccessDeniedException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        return farm.getUsers().stream()
            .map(FarmUsers::getUser)
            .map(UserDTO::new)
            .toList();
    }

    /**
     * {@code POST /api/farms} : create a farm record
     * @param farmDTO the farm's information
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws UserNotFoundException if the user doesn't exist, with status {@code 404 (NOT FOUND)}
     */
    @PostMapping
    public String createFarm(@RequestBody FarmDTO farmDTO)
        throws UnauthenticatedException, UserNotFoundException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        farmService.create(farmDTO, userLogin);
        return "Farm created.";
    }

    /**
     * {@code PUT /api/farms/update} : updates a farm record
     * @param farmId farm's id
     * @param farmDTO new farm fields
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserAccessDeniedException if the user isn't authenticated, with status {@code 401 (UNAUTHORIZED)}
     */
    @PutMapping("/update")
    public String updateFarm(
        @RequestParam("farmId") Long farmId,
        @RequestBody FarmDTO farmDTO
    ) throws UnauthenticatedException, FarmNotFoundException, UserAccessDeniedException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        // todo add privilege checking

        farmService.update(farm, farmDTO);
        return "Farm updated.";
    }


    /**
     * {@code DELETE /api/farms/delete} : deletes a farm record
     * @param farmId the deleted farm's id
     * @return {@link ResponseEntity<String>} with status {@code 200 (OK)}
     * @throws UnauthenticatedException if the client is unauthenticated, with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException if the farm doesn't exist, with status {@code 404 (NOT FOUND)}
     * @throws UserAccessDeniedException if the user isn't authenticated, with status {@code 401 (UNAUTHORIZED)}
     */
    @DeleteMapping("/delete")
    public String deleteFarm(
        @RequestParam("farmId") Long farmId
    ) throws UnauthenticatedException, FarmNotFoundException, UserAccessDeniedException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);
        farmService.delete(farm);
        return "Farm deleted.";
    }
}
