package com.example.odango.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class TasksForm {
    private int id;
    private String content;
    private short status;
    private Timestamp limitDate;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
