package com.xalts.controller;

import com.xalts.model.UserModel;
import com.xalts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This Api is used to login a user
     *
     * @param userModel
     * @return
     */
    @PostMapping("/create/user")
    public ResponseEntity<UserModel> loginUser(@RequestBody UserModel userModel){
        return new ResponseEntity<>(userService.addUser(userModel), HttpStatus.OK);
    }

    /**
     * This Api will fetch all users
     *
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * This Api is used for logging out a particular user
     *
     * @param userId
     * @return
     */
    @GetMapping("/users/logout/{userId}")
    public ResponseEntity<String> logOutUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.signOff(UUID.fromString(userId)), HttpStatus.OK);
    }
}
