package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.*;
import com.cybertech.farmcheck.repository.*;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.service.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FarmService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final FarmUsersRepository farmUsersRepository;
    private final SensorRepository sensorRepository;
    private final String defaultFarmImagePath = "static/images/default-farm-picture.png";

    private final ChatRepository chatRepository;
    private final ChatService chatService;

    @Autowired
    public FarmService(
        FarmRepository farmRepository,
        UserRepository userRepository,
        FarmUsersRepository farmUsersRepository,
        SensorRepository sensorRepository,
        ChatRepository chatRepository,
        ChatService chatService
    ) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.farmUsersRepository = farmUsersRepository;
        this.sensorRepository = sensorRepository;
        this.chatRepository = chatRepository;
        this.chatService = chatService;
    }

    /**
     * Gets a {@link Farm}.
     *
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
     *
     * @param userLogin the username of the user
     * @return a {@link List<FarmDTO>} of the farms
     * @throws UserNotFoundException if the user doesn't exist
     */
    public List<FarmDTO> getUserFarms(String userLogin) throws UserNotFoundException {
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(() -> new UserNotFoundException(userLogin));

        List<FarmDTO> farms = farmRepository
            .findAllByUserLogin(userLogin)
            .stream()
            .map(FarmDTO::new)
            .collect(Collectors.toList());

        for (FarmDTO farm : farms)
            farm.setRole(
                farmUsersRepository
                    .findFarmUsersByUserLoginAndFarmId(
                        userLogin,
                        farm.getId()
                    )
                    .get()
                    .getRole()
            );

        return farms;
    }

    /**
     * Checks if a user belongs to a farm.
     *
     * @param farm      the farm object
     * @param userLogin user's login
     */
    public void checkUserAccess(Farm farm, String userLogin)
        throws UserDeniedAccessException {
        for (FarmUsers farmUsers : farm.getUsers())
            if (Objects.equals(farmUsers.getUser().getLogin(), userLogin))
                return;
        throw new UserDeniedAccessException(userLogin, farm.getId());
    }

    /**
     * Gets the default farm image as a byte array
     * @return the byte array
     * @throws IOException if it cannot read the file
     */
    private byte[] getDefaultFarmPicture() throws IOException {
        File image = ResourceUtils.getFile("classpath:" + defaultFarmImagePath);
        FileInputStream fileInputStream = new FileInputStream(image);
        byte[] bytes = new byte[(int) image.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        return bytes;
    }

    /**
     * Creates a farm record.
     *
     * @param farmDTO   the dto from the request
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

        Farm farm = new Farm(
            farmDTO.getName(),
            farmDTO.getDescription()
        );

        // setting farm's picture
        try {
            boolean imageSetted = false;
            if (farmDTO.getImage() != null) { // checking if any image is given
                InputStream imageInputStream = new ByteArrayInputStream(farmDTO.getImage());
                if (ImageIO.read(imageInputStream) != null) { // checking if image is valid
                    log.warn("image loaded from input");
                    farm.setImage(farmDTO.getImage());
                    imageSetted = true;
                }
            }
            if (!imageSetted)
                throw new IOException();
        } catch (IOException e) {
            try {
                log.warn("image loaded as default");
                farm.setImage(getDefaultFarmPicture());
            } catch (Exception defaultPictureException) {
                log.error(
                    "Error loading the default farm picture\n" +
                    defaultPictureException.getMessage()
                );
            }
        }

        farm = farmRepository.save(farm);
        farmUsersRepository.save(
            new FarmUsers(creator, farm, (short) 1)
        );
    }

    /**
     * Adds a user to a farm.
     *
     * @param farm the farm object
     * @param user the user object
     * @param role the role of the user to the farm
     */
    public void addUserToFarm(Farm farm, User user, Short role) {
        FarmUsers farmUsers = new FarmUsers(user, farm, role);
        farmUsersRepository.save(farmUsers);
    }

    /**
     * Adds a chat to a farm.
     *
     * @param chat the chat object
     * @return the {@link Chat} saved in the db
     */
    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    /**
     * Gets all the chats of a farm
     *
     * @param farmId the id of the farm
     * @return the {@link List<Chat>}
     */
    public List<Chat> getFarmChats(Long farmId) {
        return chatRepository.findByFarmId(farmId);
    }

    /**
     * Removes a user from a farm.
     *
     * @param farm the farm object
     * @param user the user object
     */
    public void removeUserFromFarm(Farm farm, User user) {
        FarmUsers farmUsers = farmRepository
            .findByFarmIdAndUserLogin(
                farm.getId(),
                user.getLogin()
            );
        farmUsersRepository.delete(farmUsers);
    }

    /**
     * Updates a farm record.
     *
     * @param farm    the record that is going to be updated
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
     *
     * @param farm the farm record
     */
    public void delete(Farm farm) {
        for (FarmUsers farmUsers : farm.getUsers())
            farmUsersRepository.delete(farmUsers);
        for (Chat chat : farm.getChats())
            chatService.deleteChat(chat);
        for (Sensor sensor : farm.getSensors())
            sensorRepository.delete(sensor);
        farmRepository.delete(farm);
    }
}
