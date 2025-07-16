package com.example.odango.controller;

import com.example.odango.controller.form.TasksForm;
import com.example.odango.service.TaskService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Controller
public class ToDoController {
    @Autowired
    TaskService taskService;

    @Autowired
    HttpSession session;

    @GetMapping("/ToDo")
    public ModelAndView top(@RequestParam(value="searchStart",required=false) String start,
                            @RequestParam(value="searchEnd",required=false) String end,
                            @RequestParam(value = "content",required = false) String content,
                            @RequestParam(value = "searchStatus",required = false) Short status) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<TasksForm> taskData = null;

        // 後続処理用にnull詰め
        if (content == ""){
            content = null;
        }

        // タスク情報取得
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
        mav.setViewName("/top");
        mav.addObject("tasks",taskData);
        mav.addObject("searchStart", start);
        mav.addObject("searchEnd", end);
        // エラーメッセージ表示のため追記
        setErrorMessage(mav);
        return mav;
    }
    /* タスク追加画面初期表示 */
    @GetMapping("/ToDo/new")
    public ModelAndView newTask() {
        ModelAndView mav = new ModelAndView();
        TasksForm taskForm = new TasksForm();
        mav.setViewName("/new");
        mav.addObject("formModel", taskForm);
        setErrorMessage(mav);
        return mav;
    }

    /* タスク追加機能 */
    @PostMapping("/ToDo/add")
    public ModelAndView addTask(@Validated @ModelAttribute("formModel") TasksForm taskForm, BindingResult result) {
        // バリデーション
        if (result.hasErrors()) {
            List<String> messages = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                messages.add(error.getDefaultMessage());
            }
            session.setAttribute("errorMessages", messages);
            return new ModelAndView("redirect:/ToDo/new");
        }

        // ステータス:未着手で登録
        taskForm.setStatus((short) 1);
        taskService.saveTask(taskForm);
        return new ModelAndView("redirect:/ToDo");
    }

    /* 編集画面表示 */
    @GetMapping({"/ToDo/edit","/ToDo/edit/","/ToDo/edit/{id}"})
    public ModelAndView editTask(@PathVariable(required = false) String id) {
        if (this.session.getAttribute("formModel") != null){
            ModelAndView mav = new ModelAndView();
            mav.addObject("formModel", this.session.getAttribute("formModel"));
            setErrorMessage(mav);
            mav.setViewName("/edit");
            return mav;
        }

        TasksForm task = null;

        if (hasText(id) && (id.matches("^[0-9]+$"))) {
            Integer taskId = Integer.valueOf(id);
            task = taskService.editTask(taskId);
        }

        if (task == null) {
            session.setAttribute("errorMessages", "不正なパラメータです");
            return new ModelAndView("redirect:/ToDo");
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("formModel", task);
        setErrorMessage(mav);
        mav.setViewName("/edit");
        return mav;
    }

    /* 編集処理 */
    @PutMapping("/ToDo/update/{id}")
    public ModelAndView updateTask(@PathVariable Integer id, @Validated @ModelAttribute("formModel") TasksForm taskForm, BindingResult result) {
        if (result.hasErrors()) {
            List<String> messages = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                messages.add(error.getDefaultMessage());
            }
            session.setAttribute("errorMessages", messages);
            session.setAttribute("formModel", taskForm);

            return new ModelAndView("redirect:/ToDo/edit/{id}");
        }
        taskForm.setId(id);
        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
        taskForm.setUpdatedDate(updatedDate);
        taskService.saveTask(taskForm);
        return new ModelAndView("redirect:/ToDo");
    }

    /*削除処理*/
    @DeleteMapping("/ToDo/delete/{id}")
    public ModelAndView deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/ToDo");
    }

    @PutMapping("/ToDo/updateStatus/{id}")
    public ModelAndView updateStatus(@PathVariable Integer id,
                                     @ModelAttribute("tasks") TasksForm task){
        ModelAndView mav = new ModelAndView();
        taskService.updateStatus(task);
        mav.setViewName("redirect:/ToDo");
        return mav;
    }

    /* エラーメッセージ */
    private void setErrorMessage(ModelAndView mav) {
        if (session.getAttribute("errorMessages") != null) {
            mav.addObject("errorMessages", session.getAttribute("errorMessages"));
            // sessionの破棄
            session.invalidate();
        }
    }
}

