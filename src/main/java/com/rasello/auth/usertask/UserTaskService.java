package com.rasello.auth.usertask;

import com.rasello.auth.entity.Forms;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class UserTaskService {

    private TaskService taskService;
    private RuntimeService runtimeService;
    private RepositoryService repositoryService;
    private FormService formService;
    private com.rasello.auth.services.FormService entityFormService;

    public UserTaskService(TaskService taskService,
                           RuntimeService runtimeService,
                           RepositoryService repositoryService,
                           FormService formService,
                           com.rasello.auth.services.FormService entityFormService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.formService = formService;
        this.entityFormService = entityFormService;
    }

    public UserTaskDto getUserTaskDetail(String s){
            var variables = formService.getTaskFormVariables(s);
            String formName = (String) variables.get("formName");
            if(formName == null){
//                TODO: create and throw proper exceptoin
            }
            Forms entityForm = entityFormService.retrieveSchemaForResource(formName);
            entityForm.getSchema();
            UserTaskDto userTask = new UserTaskDto();
            userTask.setSchema(entityForm.getSchema());
            HashMap<String,Object>  variableMap = new HashMap<>();
            for (var entry : variables.entrySet()){
                variableMap.put(entry.getKey(), entry.getValue());
            }
            userTask.setVariables(variableMap);
            return userTask;
    }

    public void saveUserTaskForm(String s, HashMap<String, Object> formData) {
       formService.submitTaskForm(s, formData);
    }
}
