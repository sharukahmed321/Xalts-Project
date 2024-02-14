package com.xalts.service;

import com.xalts.model.UserComment;
import com.xalts.model.UserTaskModel;
import com.xalts.model.response.UserTaskResponse;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface UserTaskService {
    UserTaskResponse createTaskWithoutMail(UserTaskModel userTaskModel);

    UserTaskResponse createTaskAndSendEmail(UserTaskModel userTaskModel, List<String> emailsList);

    List<UserTaskModel> getAllTasks();

    UserTaskResponse approveTask(Long taskId, String taskApprovedBy);

    UserTaskResponse multiApproveTask(Long taskId, List<String> taskApprovalList);

    UserTaskResponse addComments(Long taskId, UserComment comment);
}
