package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.UserService;
import com.cybertech.farmcheck.service.dto.MessageDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    public FarmService farmService;
    public UserService userService;

    @Autowired
    public MessageController(
        FarmService farmService,
        UserService userService
    ) {
        this.farmService = farmService;
        this.userService = userService;
    }

    /**
     * {@code GET /api/messages} : gives all the messages of a farm.
     * @param farmId the id of the farm
     * @return the list of {@link List<MessageDTO>} with status 200
     * @throws FarmNotFoundException with status {@code 404 (NOT FOUND)}
     * @throws UnauthenticatedException with status {@code 401 (UNAUTHORIZED)}
     * @throws UserDeniedAccessException with status {@code 401 (UNAUTHORIZED)}
     */
    @GetMapping
    public List<MessageDTO> getFarmMessages(
        @RequestParam("farmId") Long farmId
    ) throws
        FarmNotFoundException,
        UnauthenticatedException,
        UserDeniedAccessException
    {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        return farmService
            .getFarmMessages(farm.getId())
            .stream()
            .map(MessageDTO::new)
            .toList();
    }
}
