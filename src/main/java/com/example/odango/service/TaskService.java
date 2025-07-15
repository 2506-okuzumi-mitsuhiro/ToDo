package com.example.odango.service;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.repository.TaskRepository;
import com.example.odango.repository.entity.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public List<TasksForm> findAll(){
        List<Tasks> results = taskRepository.findAllByOrderByLimitDateAsc();
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

    /*削除処理*/
    public void deleteTask(Integer id){
        taskRepository.deleteById(id);
    }

    /*ステータスのみ更新*/
    public void updateStatus(TasksForm tasksForm){
        tasksForm.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        Tasks tasks = setTask(tasksForm);
        taskRepository.save(tasks);
    }
    private Tasks setTask(TasksForm tasksForm){
        Tasks tasks =new Tasks();
        tasks.setId(tasksForm.getId());
        tasks.setContent(tasksForm.getContent());
        tasks.setStatus(tasksForm.getStatus());
        tasks.setLimitDate(tasksForm.getLimitDate());
        tasks.setCreatedDate(tasksForm.getCreatedDate());
        tasks.setUpdatedDate(tasksForm.getUpdatedDate());
        return tasks;
    }
}
