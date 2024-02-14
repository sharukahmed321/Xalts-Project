package com.xalts.serviceImpl;

import com.xalts.entity.UserEntity;
import com.xalts.enums.UserLoginStatus;
import com.xalts.model.UserModel;
import com.xalts.repository.UserRepository;
import com.xalts.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final EmailService emailService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    /**
     * Here we are adding a new user to database.
     * This is nothing but sign up user
     *
     * @param userModel
     * @return
     */
    @Override
    public UserModel addUser(UserModel userModel){
        UserEntity userEntity = modelMapper.map(userModel, UserEntity.class);
        userRepository.save(userEntity);
        userModel.setUserLoginId(userEntity.getUserLoginId());
        logger.info("New User signed up successfully " +userModel);
        return userModel;
    }

    /**
     * This logic is responsible to log out the user with userId
     *
     * @param userId
     * @return
     */
    @Override
    public String signOff(UUID userId){
        UserEntity userEntity = userRepository.findByUserLoginId(userId);
        if(userEntity.getUserLoginStatus() == UserLoginStatus.SIGN_IN){
            String subject = "User Sign Off Notification";
            String body = "Hello, " + userEntity.getUserName() + " has signed off.";
            List<String> emailList = new ArrayList<>();
            // Add the creator "sharukahmed321@gmail.com" to the list
            emailList.add("sharukahmed4021@gmail.com");
            emailService.sendSimpleMessage(emailList,subject, body);
            return "The user " + userEntity.getUserName() + " has signed off";
        }
        return "Error occurred in sign off";
    }

    /**
     * This is used to fetch a single user with userId
     *
     * @param userId
     * @return
     */
    @Override
    public UserModel getUser(UUID userId){
        UserEntity userEntity = userRepository.findByUserLoginId(userId);
        return modelMapper.map(userEntity, UserModel.class);
    }

    /**
     * This logic is responsible for getting all the users
     *
     * @return
     */
    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserModel.class))
                .collect(Collectors.toList());
    }


}
