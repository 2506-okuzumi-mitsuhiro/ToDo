package com.example.odango.controller;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class ToDoController {
    @Autowired
    TaskService taskService;

    @GetMapping("/ToDo")
    public ModelAndView top(@RequestParam(value="start",required=false) Timestamp start,
                            @RequestParam(value="end",required=false)Timestamp end){
        ModelAndView mav = new ModelAndView();
        List<TasksForm> taskData = taskService.findAll();
        mav.setViewName("/top");
        mav.addObject("tasks",taskData);
        mav.addObject("searchStart", start);
        mav.addObject("searchEnd", end);
        return mav;
    }

}
