package com.rasello.auth.usertask;

import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user-tasks")
public class UserTaskController {

    private  UserTaskService userTaskService;

    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @GetMapping("{id}")
    public UserTaskDto getUserTaskDetail(@PathVariable("id") String id){
        return this.userTaskService.getUserTaskDetail(id);
    }

    @PostMapping("{id}")
    public void saveUserTaskForm(@PathVariable("id") String id, @RequestBody HashMap<String, Object> formData){
        this.userTaskService.saveUserTaskForm(id, formData);
    }

}
