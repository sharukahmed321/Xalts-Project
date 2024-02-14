package com.xalts.model.response;

import com.xalts.model.UserTaskModel;

public class UserTaskResponse {
    private String text;
    private UserTaskModel userTaskModel;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserTaskModel getUserTaskModel() {
        return userTaskModel;
    }

    public void setUserTaskModel(UserTaskModel userTaskModel) {
        this.userTaskModel = userTaskModel;
    }
}
