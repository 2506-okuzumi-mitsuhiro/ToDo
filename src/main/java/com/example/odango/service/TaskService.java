package com.example.odango.service;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.repository.TaskRepository;
import com.example.odango.repository.entity.Tasks;
import io.micrometer.common.util.StringUtils;
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
        List<Tasks> results = taskRepository.findAllByOrderByLimitDateAsc();
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

        if (StringUtils.isBlank(start)){
            dateStart = format.parse("2020-01-01 00:00:00");
        }else {
            dateStart = format.parse(start + " 00:00:00");
        }

        startDate = new Timestamp(dateStart.getTime());

        if (StringUtils.isBlank(end)){
            dateEnd = format.parse("2100-12-31 23:59:59");
        }else {
            dateEnd = format.parse(end + " 23:59:59");
        }

        endDate = new Timestamp(dateEnd.getTime());

        List<Tasks> results = null;

        if (!StringUtils.isBlank(content) && status != null) {
            results = taskRepository.findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc(startDate, endDate, content, status);
        } else if (!StringUtils.isBlank(content)) {
            results = taskRepository.findByLimitDateBetweenAndContentOrderByLimitDateAsc(startDate, endDate, content);
        } else if (status != null) {
            results = taskRepository.findByLimitDateBetweenAndStatusOrderByLimitDateAsc(startDate, endDate, status);
        } else {
            results = taskRepository.findByLimitDateBetweenOrderByLimitDateAsc(startDate, endDate);
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
        task.setLimitDate(reqTask.getLimitDate());
        task.setCreatedDate(reqTask.getCreatedDate());
        task.setUpdatedDate(reqTask.getUpdatedDate());
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