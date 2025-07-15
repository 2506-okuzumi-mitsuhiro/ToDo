package com.example.odango.controller;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class ToDoController {
    @Autowired
    TaskService taskService;

    @GetMapping("/ToDo")
    public ModelAndView top(@RequestParam(value = "start", required = false) Timestamp start,
                            @RequestParam(value = "end", required = false) Timestamp end) {
        ModelAndView mav = new ModelAndView();
        List<TasksForm> taskData = taskService.findAll();
        mav.setViewName("/top");
        mav.addObject("tasks", taskData);
        mav.addObject("searchStart", start);
        mav.addObject("searchEnd", end);
        return mav;
    }

    /* タスク追加画面初期表示 */
    @GetMapping("/ToDo/new")
    public ModelAndView newTask() {
        ModelAndView mav = new ModelAndView();
        TasksForm taskForm = new TasksForm();
        mav.setViewName("/new");
        mav.addObject("formModel", taskForm);
        return mav;
    }

    /* タスク追加機能 */
    @PostMapping("/ToDo/add")
    public ModelAndView addTask(@Validated @ModelAttribute("formModel") TasksForm taskForm, BindingResult result) {
        // ステータス:未着手で登録
        taskForm.setStatus((short) 1);
        taskService.saveTask(taskForm);
        return new ModelAndView("redirect:/ToDo");
    }

    /*削除処理*/
    @DeleteMapping("/ToDo/delete/{id}")
    public ModelAndView deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/ToDo");
    }
}