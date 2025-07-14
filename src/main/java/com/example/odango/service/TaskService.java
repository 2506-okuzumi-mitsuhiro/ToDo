package com.example.odango.service;

import com.example.odango.controller.form.TaskForm;
import com.example.odango.repository.TaskRepository;
import com.example.odango.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskService {
    @Autowired
    TaskRepository taskRepository;


    /* レコード追加・更新 */
//    public void saveTask(TaskForm reqTask) {
//        Task saveTask = setTaskEntity(reqTask);
//        taskRepository.save(saveTask);
//    }

    /* リクエストから取得した情報をEntityに設定 */
    private Task setTaskEntity(TaskForm reqTask) {
        Task task = new Task();
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setLimitDate(reqTask.getLimitDate());
        task.setCreatedDate(reqTask.getCreatedDate());
        task.setUpdatedDate(reqTask.getUpdatedDate());
        return task;
    }
}
