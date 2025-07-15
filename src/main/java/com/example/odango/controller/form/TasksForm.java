package com.example.odango.controller.form;

<<<<<<< HEAD
=======
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
>>>>>>> b5596057f9807704bf3a6b5b3ab63b6c250be5d9
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class TasksForm {
    private int id;
<<<<<<< HEAD
    private String content;
    private short status;
    private Timestamp limitDate;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
=======

    @NotBlank(message = "タスクを入力してください")
    @Size(max = 140, message = "タスクは140文字以内で入力してください")
    private String content;

    private short status;

    @FutureOrPresent(message = "無効な日付です")
    private Timestamp limitDate;

    // limitDateを文字列で取得するために追加
    @NotBlank(message = "期限を設定してください")
    private String strLimitDate;

    private Timestamp createdDate;
    private Timestamp updatedDate;
}
>>>>>>> b5596057f9807704bf3a6b5b3ab63b6c250be5d9
