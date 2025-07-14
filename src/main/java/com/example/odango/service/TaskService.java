package com.example.odango.service;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.repository.TaskRepository;
import com.example.odango.repository.entity.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public List<TasksForm> findAll(){
        List<Tasks> results = taskRepository.findAll();
        List<TasksForm> tasks = setTaskForm(results);
        return tasks;
    }
    private List<TasksForm> setTaskForm(List<Tasks> results){
        List<TasksForm> tasks = new ArrayList<>();
        for(Tasks result : results){
            TasksForm task = new TasksForm();
            task.setId(result.getId());
            task.setContent(result.getContent());
            task.setStatus(result.getStatus());
            task.setLimitDate(result.getLimitDate());
            task.setCreatedDate(result.getCreatedDate());
            task.setUpdatedDate(result.getUpdatedDate());
            tasks.add(task);
        }
        return tasks;
    }
}
