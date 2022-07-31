package com.rasello.auth.usertask;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.HashMap;

@Data
@Getter
@Setter
public class UserTaskDto {
    private HashMap<String,Object> schema = new HashMap<>();
    private HashMap<String,Object> variables = new HashMap<>();
}
