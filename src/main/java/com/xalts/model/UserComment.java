package com.xalts.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserComment {
    private String userName;
    private String userComment;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
