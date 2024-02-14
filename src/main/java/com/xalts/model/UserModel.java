package com.xalts.model;


import com.xalts.enums.UserLoginStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UserModel {

    private UUID userLoginId;
    private String userName;
    private String userMail;
    private UserLoginStatus userLoginStatus;

    public UUID getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(UUID userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public UserLoginStatus getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(UserLoginStatus userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }
}
