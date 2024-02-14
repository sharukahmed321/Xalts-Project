package com.xalts.controller;

import com.xalts.model.UserComment;
import com.xalts.model.UserTaskModel;
import com.xalts.model.response.UserTaskResponse;
import com.xalts.service.UserTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserTaskController {

    private final UserTaskService userTaskService;

    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }


    /**
     * This Api helps us to create a task, I have used this only to create user
     * and test it in my local. I'm not sending any mails here.
     *
     * @param userTaskModel
     * @return
     */
    @PostMapping("task/create")
    public ResponseEntity<UserTaskResponse> createTask(@RequestBody UserTaskModel userTaskModel){
        return new ResponseEntity<>(userTaskService.createTaskWithoutMail(userTaskModel), HttpStatus.OK);
    }

    /**
     * This Api will helps us to create a user and also send mails to relevant users
     *
     * @param userTaskModel
     * @param emails
     * @return
     */
    @PostMapping("task/createAndEmail")
    public ResponseEntity<UserTaskResponse> createTaskAndSendEmail(@RequestBody UserTaskModel userTaskModel, @RequestParam List<String> emails){
        return new ResponseEntity<>(userTaskService.createTaskAndSendEmail(userTaskModel, emails), HttpStatus.OK);
    }

    /**
     * This will help in fetching all the tasks
     *
     * @return
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<UserTaskModel>> getAllTasks(){
        return new ResponseEntity<>(userTaskService.getAllTasks(), HttpStatus.OK);
    }

    /**
     * This Api is responsible to approve a task, when three people gives approval,
     * status would be changed to approval
     *
     * @param taskId
     * @param taskApprovedBy
     * @return
     */
    @GetMapping("tasks/approve/{taskId}")
    public ResponseEntity<UserTaskResponse> approveTask(@PathVariable Long taskId, @RequestParam String taskApprovedBy){
        return new ResponseEntity<>(userTaskService.approveTask(taskId, taskApprovedBy), HttpStatus.OK);
    }

    /**
     * This Api will help us add comments to a particular taskId
     *
     * @param comment
     * @param taskId
     * @return
     */
    @PostMapping("/tasks/{taskId}/addComment")
    public ResponseEntity<UserTaskResponse> addComments(@RequestBody UserComment comment, @PathVariable Long taskId){
        return new ResponseEntity<>(userTaskService.addComments(taskId, comment), HttpStatus.OK);
    }

//    @GetMapping("tasks/approve/{taskId}")
//    public ResponseEntity<UserTaskResponse> multiApprovalTask(@PathVariable Long taskId, @RequestParam List<String> taskApprovalList){
//        return new ResponseEntity<>(userTaskService.multiApproveTask(taskId, taskApprovalList), HttpStatus.OK);
//    }
}
