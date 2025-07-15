package com.example.odango.controller;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.service.TaskService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
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
                            @RequestParam(value = "searchStatus",required = false) Short status) throws ParseException{
        ModelAndView mav = new ModelAndView();
        List<TasksForm> taskData = null;

        // タスク絞り込み処理
        if (StringUtils.isBlank(start) && StringUtils.isBlank(end) && StringUtils.isBlank(content) && status == null){
            // 全件取得
            taskData = taskService.findAll();
        }else {
            // 条件取得
            taskData = taskService.findNarrowDownTask(start, end, content, status);

            // 各条件を再表示させる為にmavに渡す
            if(!StringUtils.isBlank(start)) {
                mav.addObject("searchStart", start);
            }
            if(!StringUtils.isBlank(end)) {
                mav.addObject("searchEnd", end);
            }
            if(!StringUtils.isBlank(content)){
                mav.addObject("searchContent", content);
            }
            if (status != null){
                mav.addObject("searchStatus", status);
            }
        }

        mav.setViewName("/top");
        mav.addObject("tasks",taskData);
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