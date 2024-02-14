package com.xalts.serviceImpl;

import com.xalts.entity.UserTaskEntity;
import com.xalts.enums.TaskStatus;
import com.xalts.model.UserComment;
import com.xalts.model.UserModel;
import com.xalts.model.UserTaskModel;
import com.xalts.model.response.UserTaskResponse;
import com.xalts.repository.UserTaskRepository;
import com.xalts.service.UserService;
import com.xalts.service.UserTaskService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserTaskServiceImpl implements UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EmailService emailService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserTaskServiceImpl(UserTaskRepository userTaskRepository, ModelMapper modelMapper, UserService userService, EmailService emailService) {
        this.userTaskRepository = userTaskRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.emailService = emailService;
    }

    /**
     * This function will create a user but will not send any mail,
     * I have used this for testing purpose
     *
     * @param userTaskModel
     * @return
     */
    @Override
    public UserTaskResponse createTaskWithoutMail(UserTaskModel userTaskModel){
        UserTaskResponse userTaskResponse = new UserTaskResponse();
        // fetch all users
        List<UserModel> userModels = userService.getAllUsers();

        // check whether existing user in DB created the task
        // or some random user without registration
        for(UserModel userModel : userModels){
            if(userModel.getUserName().equals(userTaskModel.getTaskCreatedBy())){
                UserTaskEntity userTaskEntity = modelMapper.map(userTaskModel, UserTaskEntity.class);
                userTaskRepository.save(userTaskEntity);
                userTaskModel.setTaskId(userTaskEntity.getTaskId());
                userTaskResponse.setText("New Task created Successfully");
                userTaskResponse.setUserTaskModel(userTaskModel);
                return userTaskResponse;
            }
        }
        userTaskResponse.setText("Not an Authorized User To create Task");
        userTaskResponse.setUserTaskModel(null);
        return userTaskResponse;
    }


    /**
     * This Function will create a user and also responsible for sending mails
     *
     * @param userTaskModel
     * @param emailsList
     * @return
     */
    @Override
    public UserTaskResponse createTaskAndSendEmail(UserTaskModel userTaskModel, List<String> emailsList){
        UserTaskResponse userTaskResponse = new UserTaskResponse();
        // fetch all users
        List<UserModel> userModels = userService.getAllUsers();

        // check whether existing user in DB created the task
        // or some random user without registration
        for(UserModel userModel : userModels){
            if(userModel.getUserName().equals(userTaskModel.getTaskCreatedBy())){
                UserTaskEntity userTaskEntity = modelMapper.map(userTaskModel, UserTaskEntity.class);
                userTaskRepository.save(userTaskEntity);
                userTaskModel.setTaskId(userTaskEntity.getTaskId());
                userTaskResponse.setText("New Task created Successfully");
                userTaskResponse.setUserTaskModel(userTaskModel);
                // send email
                String subject = "New Task is Created";
                String body = "Hello, A new task has been created with task id "+ userTaskEntity.getTaskId()
                        + ", task name " + userTaskEntity.getTaskName()
                        + ", task description " + userTaskEntity.getTaskDescription()
                        + " and task createdBy " + userTaskEntity.getTaskCreatedBy();

                emailService.sendSimpleMessage(emailsList,subject, body);

                return userTaskResponse;
            }
        }
        userTaskResponse.setText("Not an Authorized User To create Task");
        userTaskResponse.setUserTaskModel(null);
        return userTaskResponse;
    }

    /**
     * This function will gives us getAllTasks
     *
     * @return
     */
    @Override
    public List<UserTaskModel> getAllTasks(){
        List<UserTaskEntity> userTaskEntities = userTaskRepository.findAll();
        return userTaskEntities.stream()
                .map(userTaskEntity -> modelMapper.map(userTaskEntity, UserTaskModel.class))
                .collect(Collectors.toList());
    }

    /**
     * This is a helper function to check when task exists in DB or not
     *
     * @param taskId
     * @return
     */
    public boolean taskExistsInDbOrNot(Long taskId){
        List<UserTaskModel> userTaskModels = getAllTasks();
        for(UserTaskModel userTaskModel : userTaskModels){
            if(userTaskModel.getTaskId() == taskId){
                return true;
            }
        }
        return false;
    }

    /**
     * This function will approve the task, if no of approvals >= 3
     * then status of task is updated to APPROVED
     *
     * @param taskId
     * @param taskApprovedBy
     * @return
     */
    @Override
    public UserTaskResponse approveTask(Long taskId, String taskApprovedBy){
        UserTaskResponse userTaskResponse = new UserTaskResponse();
        if(!taskExistsInDbOrNot(taskId)){
            userTaskResponse.setText("Task doesn't Exist");
            userTaskResponse.setUserTaskModel(null);
            return userTaskResponse;
        }
        // fetch all users
        List<UserModel> userModels = userService.getAllUsers();
        // check whether existing user in DB approved the task
        // or some random user without registration
        for(UserModel userModel : userModels) {
            if (userModel.getUserName().equals(taskApprovedBy)) {
                Optional<UserTaskEntity> userTaskEntityOpt = userTaskRepository.findByTaskId(taskId);
                if (userTaskEntityOpt.isPresent()) {
                    UserTaskEntity userTaskEntity = userTaskEntityOpt.get();
                    if(userTaskEntity.getTaskApprovalList().contains(taskApprovedBy)){
                        userTaskResponse.setText(taskApprovedBy +" has already approved");
                        UserTaskModel userTaskModel = modelMapper.map(userTaskEntity, UserTaskModel.class);
                        userTaskResponse.setUserTaskModel(userTaskModel);
                        return userTaskResponse;
                    }
                    List<String> taskApprovalList = userTaskEntity.getTaskApprovalList();
                    if (taskApprovalList == null) {
                        taskApprovalList = new ArrayList<>();
                    }
                    taskApprovalList.add(taskApprovedBy);
                    userTaskEntity.setTaskApprovalList(taskApprovalList);
                    // Approve only if you have more than 3 approvals
                    if(taskApprovalList.size() >=3){
                        userTaskEntity.setTaskStatus(TaskStatus.APPROVED);
                        userTaskResponse.setText("Task was successfully approved by " + taskApprovalList);
                    } else {
                        userTaskResponse.setText("Need at least Three Approvals, you got only " + taskApprovalList.size());
                    }
                    userTaskRepository.save(userTaskEntity);
                    UserTaskModel userTaskModel = modelMapper.map(userTaskEntity, UserTaskModel.class);
                    userTaskResponse.setUserTaskModel(userTaskModel);
                    return userTaskResponse;
                }
            }
        }
        userTaskResponse.setText("Not an Authorized User To approve Task");
        userTaskResponse.setUserTaskModel(null);
        return userTaskResponse;
    }


    /**
     * This is for testing purpose, Not a requirement in this project
     *
     * @param taskId
     * @param taskApprovalList
     * @return
     */
    @Override
    public UserTaskResponse multiApproveTask(Long taskId, List<String> taskApprovalList) {
        UserTaskResponse userTaskResponse = new UserTaskResponse();
        // fetch all users
        List<UserModel> userModels = userService.getAllUsers();
        // check whether existing users in DB approved the task
        // or some random user without registration

        int approvalCount = 0;
        for (UserModel userModel : userModels) {
            for (String taskApprovedBy : taskApprovalList) {
                if (userModel.getUserName().equals(taskApprovedBy)) {
                    approvalCount++;
                }
            }
        }
        if (approvalCount >= 3) {
            Optional<UserTaskEntity> userTaskEntityOpt = userTaskRepository.findByTaskId(taskId);
            if (userTaskEntityOpt.isPresent()) {
                UserTaskEntity userTaskEntity = userTaskEntityOpt.get();
                userTaskEntity.setTaskStatus(TaskStatus.APPROVED);
                userTaskEntity.setTaskApprovalList(taskApprovalList);
                userTaskRepository.save(userTaskEntity);
                UserTaskModel userTaskModel = modelMapper.map(userTaskEntity, UserTaskModel.class);
                userTaskResponse.setText("Task was successfully approved by " + taskApprovalList);
                userTaskResponse.setUserTaskModel(userTaskModel);
                return userTaskResponse;
            }
        }
        userTaskResponse.setText("Need at least Three Approvals, you got only " + approvalCount);
        userTaskResponse.setUserTaskModel(null);
        return userTaskResponse;
    }

    /**
     * This function will help us to addComments
     *
     * @param taskId
     * @param comment
     * @return
     */
    @Override
    public UserTaskResponse addComments(Long taskId, UserComment comment){
        UserTaskResponse userTaskResponse = new UserTaskResponse();
        // fetch all users
        List<UserModel> userModels = userService.getAllUsers();
        for(UserModel userModel : userModels){
            if(userModel.getUserName().equals(comment.getUserName())){
                Optional<UserTaskEntity> userTaskEntityOptional = userTaskRepository.findByTaskId(taskId);
                if(userTaskEntityOptional.isPresent()){
                    UserTaskEntity userTaskEntity = userTaskEntityOptional.get();
                    List<UserComment> commentList = userTaskEntity.getUserComments();
                    if(commentList == null){
                        commentList = new ArrayList<>();
                    }
                    commentList.add(comment);
                    userTaskEntity.setUserComments(commentList);
                    userTaskRepository.save(userTaskEntity);
                    UserTaskModel userTaskModel = modelMapper.map(userTaskEntity, UserTaskModel.class);
                    userTaskResponse.setUserTaskModel(userTaskModel);
                    return userTaskResponse;
                }
            }
        }
        userTaskResponse.setText("Outside Users cannot comment");
        userTaskResponse.setUserTaskModel(null);
        return  userTaskResponse;
    }

}
