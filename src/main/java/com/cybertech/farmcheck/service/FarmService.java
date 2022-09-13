package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.FarmUsers;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.repository.FarmRepository;
import com.cybertech.farmcheck.repository.FarmUsersRepository;
import com.cybertech.farmcheck.repository.UserRepository;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final FarmUsersRepository farmUsersRepository;

    @Autowired
    public FarmService(
        FarmRepository farmRepository,
        UserRepository userRepository,
        FarmUsersRepository farmUsersRepository
    ) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.farmUsersRepository = farmUsersRepository;
    }

    public List<Farm> getUserFarms(String userLogin) throws UserNotFoundException {
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        return farmRepository.findAllByUserLogin(userLogin);
    }

    public void create(
        FarmDTO farmDTO,
        String userLogin
    ) throws UserNotFoundException {
        User creator = userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        Farm farm = new Farm(farmDTO.getName(), farmDTO.getImage());
        farm = farmRepository.save(farm);
        farmUsersRepository.save(
            new FarmUsers(creator, farm, (short)1)
        );
    }
}
