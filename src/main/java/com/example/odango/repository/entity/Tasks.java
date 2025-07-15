package com.example.odango.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Tasks {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;

    @Column
    private short status;

    @Column(name = "limit_date")
    private Timestamp limitDate;

    // limitDateを文字列で取得するために追加
    @Transient
    private String strLimitDate;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", insertable = false)
    private Timestamp updatedDate;
}