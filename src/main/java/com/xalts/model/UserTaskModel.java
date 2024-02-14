package com.xalts.model;

import com.xalts.enums.TaskStatus;

import javax.xml.stream.events.Comment;
import java.util.List;


public class UserTaskModel {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private String taskCreatedBy;
    private TaskStatus taskStatus;
    private List<String> taskApprovalList;
    private List<UserComment> userComments;
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(String taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<String> getTaskApprovalList() {
        return taskApprovalList;
    }

    public void setTaskApprovalList(List<String> taskApprovalList) {
        this.taskApprovalList = taskApprovalList;
    }

    public List<UserComment> getUserComments() {
        return userComments;
    }

    public void setUserComments(List<UserComment> userComments) {
        this.userComments = userComments;
    }
}
