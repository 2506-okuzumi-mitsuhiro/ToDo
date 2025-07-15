package com.example.odango.controller;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ToDoController {
    @Autowired
    TaskService taskService;

    /*画面表示処理*/
    @GetMapping("/ToDo")
    public ModelAndView top(@RequestParam(value="searchStart",required=false) String start,
                            @RequestParam(value="searchEnd",required=false) String end,
                            @RequestParam(value = "searchContent",required = false) String content,
                            @RequestParam(value = "searchStatus",required = false) short status){
        ModelAndView mav = new ModelAndView();
        List<TasksForm> taskData = null;

        // タスク絞り込み処理
        if (start.isBlank() && end.isBlank() && content.isBlank() && status == 0){
            // 全件取得
            taskData = taskService.findAll();
        }else {
            // 条件取得
            taskData = taskService.findNarrowDownTask(start, end, content, status);

            // 各条件を再表示させる為にmavに渡す
            if(!start.isBlank()) {
                mav.addObject("searchStart", start);
            }
            if(!end.isBlank()) {
                mav.addObject("searchEnd", end);
            }
            if(!content.isBlank()){
                mav.addObject("searchContent", content);
            }
            if (status != 0){
                mav.addObject("searchStatus", status);
            }
        }

        mav.setViewName("/top");
        mav.addObject("tasks",taskData);
        return mav;
    }

    /*削除処理*/
    @DeleteMapping("/ToDo/delete/{id}")
    public ModelAndView deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/ToDo");
    }
}