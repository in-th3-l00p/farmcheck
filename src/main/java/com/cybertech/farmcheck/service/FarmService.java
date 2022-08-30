package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.FarmUsers;
import com.cybertech.farmcheck.domain.User;
import com.cybertech.farmcheck.repository.FarmRepository;
import com.cybertech.farmcheck.repository.FarmUsersRepository;
import com.cybertech.farmcheck.repository.UserRepository;
import com.cybertech.farmcheck.service.dto.FarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FarmUsersRepository farmUsersRepository;

    /*public Farm save(AnuntDto anuntDto, Long creatorId) throws ParseException {
        Anunt anunt = new Anunt(
            anuntDto.getTitle(),
            anuntDto.getDescription(),
            cityService.findById(anuntDto.getCityId()),
            dateParser(anuntDto.getDateFrom()),
            dateParser(anuntDto.getDateTo()),
            userService.loadUserById(creatorId));
        return anuntRepository.save(anunt);
    }

    public Anunt save(Anunt anunt) {
        return anuntRepository.save(anunt);
    }

    public List<Anunt> findAllByCreator(Long creatorId){
        User user = userService.loadUserById(creatorId);
        return anuntRepository.findAllByCreator(user);
    }*/

    public List<Farm> findAll(){
        return farmRepository.findAll();
    }

    public void create(FarmDTO farmDTO, String userLogin){
        Farm farm = new Farm(farmDTO.getName(),farmDTO.getImage());
        Farm savedFarm = farmRepository.save(farm);
        User creator = userRepository.findOneByLogin(userLogin).get();
        FarmUsers farmUsers = new FarmUsers(creator,savedFarm, (short) 1);
        farmUsersRepository.save(farmUsers);
    }

    /*public List<Anunt> findAll(Specification<Anunt> searchCriteria){
        return anuntRepository.findAll(searchCriteria);
    }

    public Anunt loadAnuntById(Long id) {
        return anuntRepository.findById(id).orElseThrow(() -> new AnuntNotFoundException("Anunt Not Found with id: " + id));
    }

    public Date dateParser(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }

    public String dateParser(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public AnuntResponse mapAnunt(Anunt anunt){
        return new AnuntResponse(
            anunt.getId(),
            anunt.getTitle(),
            anunt.getDescription(),
            anunt.getCity().getName(),
            dateParser(anunt.getDateFrom()),
            dateParser(anunt.getDateTo()),
            anunt.getCreator().getId(),
            anunt.getCreator().getUsername()
        );
    }*/
}
