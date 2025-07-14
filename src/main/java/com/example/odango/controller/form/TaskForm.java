package com.example.odango.controller.form;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Getter
@Setter

public class TaskForm {
    private int id;

    // バリデーション(空欄でない、文字数)
    @NotBlank
    @Length(max = 140)
    private String content;

    private short status;

    // バリデーション(空欄でない、日付が未来か今日であるか)
    @NotBlank
    @FutureOrPresent
    private Timestamp limitDate;

    private Timestamp createdDate;
    private Timestamp updatedDate;
}

