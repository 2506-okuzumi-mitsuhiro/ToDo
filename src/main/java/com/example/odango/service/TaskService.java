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

    /*全件取得処理*/
    public List<TasksForm> findAll(){
        List<Tasks> results = taskRepository.findAll();
        List<TasksForm> tasks = setTaskForm(results);
        return tasks;
    }

    /*条件対象取得処理*/
    public List<TasksForm> findNarrowDownTask(String start, String end,
                                              String content, Short status) throws ParseException {
        Timestamp startDate = null;
        Timestamp endDate = null;
        Date dateStart = null;
        Date dateEnd = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (start.isBlank()){
            dateStart = format.parse("2020-01-01 00:00:00");
        }else {
            dateStart = format.parse(start + " 00:00:00");
        }

        startDate = new Timestamp(dateStart.getTime());

        if (end.isBlank()){
            dateEnd = format.parse("2100-12-31 23:59:59");
        }else {
            dateEnd = format.parse(end + " 23:59:59");
        }

        endDate = new Timestamp(dateEnd.getTime());

        List<Tasks> results = null;

        if (!content.isBlank() && status != 0) {
            results = taskRepository.findByLimitDateBetweenAndContentAndStatus(startDate, endDate, content, status);
        } else if (!content.isBlank()) {
            results = taskRepository.findByLimitDateBetweenAndStatus(startDate, endDate, content);
        } else if (status != 0) {
            results = taskRepository.findByLimitDateBetweenAndContent(startDate, endDate, status);
        } else {
            results = taskRepository.findByLimitDateBetween(startDate, endDate);
        }

        List<TasksForm> tasks = setTaskForm(results);
        return tasks;
    }

    /*DB>>Form処理*/
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
    public void deleteTask(Integer id){
        taskRepository.deleteById(id);
    }
}