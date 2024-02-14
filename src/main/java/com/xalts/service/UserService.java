package com.xalts.service;

import com.xalts.model.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserModel addUser(UserModel userModel);

    String signOff(UUID userId);

    UserModel getUser(UUID userId);

    List<UserModel> getAllUsers();
}
