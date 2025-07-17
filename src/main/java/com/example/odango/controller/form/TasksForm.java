package com.example.odango.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class TasksForm {
    private int id;

    @NotBlank(message = "タスクを入力してください")
    @Size(max = 140, message = "タスクは140文字以内で入力してください")
    private String content;

    // 全角スペースのみが入力された時のバリデーション
    @AssertTrue(message = "タスクを入力してください")
    public boolean isValidContent() {
        if (content.matches("^[　]+$")) {
            return false;
        } else {
            return true;
        }
    }

    private short status;

    @NotNull(message = "期限を設定してください")
    @FutureOrPresent(message = "無効な日付です")
    private LocalDateTime limitDate;

    private Timestamp createdDate;
    private Timestamp updatedDate;
}