package com.example.odango.controller;

import com.example.odango.controller.form.TaskForm;
import com.example.odango.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

public class ToDoController {
    @Autowired
    TaskService taskService;

    @Autowired
    HttpSession session;

    /* 2-1.タスク追加画面初期表示(newページへの移動) */
    @GetMapping("/new")
    public ModelAndView newTask() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        TaskForm taskForm = new TaskForm();
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", taskForm);
        // バリデーションメッセージ
        setErrorMessage(mav);
        return mav;
    }

    /* 2-2.タスク追加機能 */
    @PostMapping("/add")
    public ModelAndView addTask(@Validated @ModelAttribute("formModel") TaskForm taskForm, BindingResult result) {
        // バリデーション
        if (result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()) {
                String field = error.getField();
                String message = error.getDefaultMessage();
            }
            return new ModelAndView("redirect:/new");
        }

        // タスクをテーブルに格納
//        taskService.saveTask(taskForm);
        // TOP画面へへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /* バリデーションメッセージ設定 */
    private void setErrorMessage(ModelAndView mav) {
        if (session.getAttribute("errorMessages") != null) {
            mav.addObject("errorMessages", session.getAttribute("errorMessages"));
            // sessionの破棄
            session.invalidate();
        }
    }
}
