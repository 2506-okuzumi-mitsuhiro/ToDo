package com.example.odango.service;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.repository.TaskRepository;
import com.example.odango.repository.entity.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private List<TasksForm> setTaskForm(List<Tasks> results) {
        List<TasksForm> tasks = new ArrayList<>();
        for (Tasks result : results) {
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

    /* レコード追加・更新 */
    public void saveTask(TasksForm reqTask) {
        Tasks saveTask = setTaskEntity(reqTask);
        taskRepository.save(saveTask);
    }

    private Tasks setTaskEntity(TasksForm reqTask) {
        Tasks task = new Tasks();
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setStatus(reqTask.getStatus());
        task.setCreatedDate(reqTask.getCreatedDate());
        task.setUpdatedDate(reqTask.getUpdatedDate());

        // strLimitDate(String) → limitDate(TimeStamp)
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = reqTask.getStrLimitDate();
            Date date = sdf.parse(str);
            task.setLimitDate(new Timestamp(date.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    /*削除処理*/
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    /*ステータスのみ更新*/
    public void updateStatus(TasksForm tasksForm){
        tasksForm.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        Tasks tasks = setTask(tasksForm);
        taskRepository.save(tasks);
    }
    private Tasks setTask(TasksForm reqTask) {
        Tasks task = new Tasks();
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setStatus(reqTask.getStatus());
        task.setLimitDate(reqTask.getLimitDate());
        task.setCreatedDate(reqTask.getCreatedDate());
        task.setUpdatedDate(reqTask.getUpdatedDate());
        return task;
    }
}