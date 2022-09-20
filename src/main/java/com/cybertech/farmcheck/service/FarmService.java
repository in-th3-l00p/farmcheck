package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.FarmUsers;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.repository.FarmRepository;
import com.cybertech.farmcheck.repository.FarmUsersRepository;
import com.cybertech.farmcheck.repository.UserRepository;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserAccessDeniedException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * Gets a {@link Farm}.
     * @param farmId farm's id
     * @return the Farm object if found
     * @throws FarmNotFoundException if the farm doesn't exist
     */
    public Farm getFarm(Long farmId) throws FarmNotFoundException {
        return farmRepository.findById(farmId)
            .orElseThrow(() -> new FarmNotFoundException(farmId));
    }

    /**
     * Gets all the farms of a user.
     * @param userLogin the username of the user
     * @return a {@link List<FarmDTO>} of the farms
     * @throws UserNotFoundException if the user doesn't exist
     */
    public List<FarmDTO> getUserFarms(String userLogin) throws UserNotFoundException {
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        return farmRepository
            .findAllByUserLogin(userLogin)
            .stream()
            .map(FarmDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * Checks if a user has access to a farm.
     * @param farm the farm object
     * @param userLogin user's login
     * @throws UserAccessDeniedException if the user has no access to the farm
     */
    public void checkUserAccess(Farm farm, String userLogin)
        throws UserAccessDeniedException
    {
        for (FarmUsers farmUsers : farm.getUsers())
            if (Objects.equals(farmUsers.getUser().getLogin(), userLogin))
                return;
        throw new UserAccessDeniedException(userLogin, farm.getId());
    }

    /**
     * Creates a farm record.
     * @param farmDTO the dto from the request
     * @param userLogin the owner's login
     * @throws UserNotFoundException if the owner doesn't exist
     */
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

    /**
     * Updates a farm record.
     * @param farm the record that is going to be updated
     * @param farmDTO the dto from the request
     */
    public void update(Farm farm, FarmDTO farmDTO) {
        if (farmDTO.getName() != null)
            farm.setName(farmDTO.getName());
        if (farmDTO.getImage() != null)
            farm.setImage(farmDTO.getImage());
        farmRepository.save(farm);
    }

    /**
     * Deletes a farm record.
     * @param farm the farm record
     */
    public void delete(Farm farm) {
        farmRepository.delete(farm);
    }
}
