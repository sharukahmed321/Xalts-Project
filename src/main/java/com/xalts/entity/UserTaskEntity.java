package com.xalts.entity;

import com.xalts.enums.TaskStatus;
import com.xalts.model.UserComment;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_tasks")
public class UserTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_created_by")
    private String taskCreatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Column(name = "task_approval_list")
    private List<String> taskApprovalList;

    @ElementCollection
    @CollectionTable(name = "user_task_comments", joinColumns = @JoinColumn(name = "task_id"))
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
