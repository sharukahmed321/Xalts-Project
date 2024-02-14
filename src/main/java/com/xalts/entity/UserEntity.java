package com.xalts.entity;

import com.xalts.enums.UserLoginStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "Users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_login_id", updatable = false, nullable = false)
    private UUID userLoginId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_mail")
    private String userMail;

    @Column(name = "user_login_status")
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