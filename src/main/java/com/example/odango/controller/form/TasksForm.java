package com.example.odango.controller.form;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
public class TasksForm {
    private int id;

    @NotBlank(message = "タスクを入力してください")
    @Size(max = 140, message = "タスクは140文字以内で入力してください")
    private String content;

    private short status;

    @NotNull(message = "期限を設定してください")
    @FutureOrPresent(message = "無効な日付です")
    private LocalDate limitDate;

    private Timestamp createdDate;
    private Timestamp updatedDate;
}