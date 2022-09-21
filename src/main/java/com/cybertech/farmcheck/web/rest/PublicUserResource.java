package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.UserService;
import com.cybertech.farmcheck.service.dto.UserDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey")
    );

    private final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    private final UserService userService;

    private final FarmService farmService;

    public PublicUserResource(
        UserService userService,
        FarmService farmService
    ) {
        this.userService = userService;
        this.farmService = farmService;
    }

    /**
     * Adds a user to a farm.
     * @param farmId the id of the farm
     * @param userLogin the added user's username
     * @return status message
     * @throws UnauthenticatedException with status {@code 401 (UNAUTHORIZED)}
     * @throws FarmNotFoundException with status {@code 404 (NOT_FOUND)}
     * @throws UserDeniedAccessException with status {@code 401 (UNAUTHORIZED)}
     */
    @PutMapping("/user/addFarm")
    public String addUserToFarm(
        @RequestParam("farmId") Long farmId,
        @RequestParam("userLogin") String userLogin
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException
    {
        String authenticatedUserLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, authenticatedUserLogin);
        User user = userService
            .getUserWithAuthoritiesByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        farmService.addUserToFarm(farm, user, (short)2);

        // checking if the user has access to the farm
        return "User added";
    }

    /**
     * Delete a user from a farm.
     * @param farmId the id of the farm
     * @param userLogin the removed user's login
     * @return status message
     */
    @DeleteMapping("/user/removeFarm")
    public ResponseEntity<String> removeUserFromFarm(
        @RequestParam("farmId") Long farmId,
        @RequestParam("userLogin") String userLogin
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException
    {
        String authenticatedUserLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);
        if (Objects.equals(authenticatedUserLogin, userLogin)) {
            return ResponseEntity
                .badRequest()
                .body("You cannot remove yourself");
        }

        // todo privileges check

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, authenticatedUserLogin);
        User user = userService
            .getUserWithAuthoritiesByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));
        farmService.removeUserFromFarm(farm, user);
        return ResponseEntity.ok("User removed.");
    }

    /**
     * {@code GET /users} : get all users with only the public informations - calling this are allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /user} : get the user that has the login given as param - calling this is allowed for everyone.
     * @param login user's login
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of the user.
     */
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getPublicUserById(@Param("login") String login) {
        log.debug("REST request to get a public user by id");
        final Optional<UserDTO> userDTO = userService.getPublicUserByLogin(login);
        return ResponseEntity.ok(
            userDTO.orElseThrow(
                () -> new UserNotFoundException(login)
            )
        );
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * Gets a list of all roles.
     * @return a string list of all roles.
     */
    @GetMapping("/authorities")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }
}
