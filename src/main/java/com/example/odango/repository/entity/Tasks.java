package com.example.odango.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Tasks {
    @Id
    @Column
    private int id;

    @Column
    private String content;

    @Column
    private short status;

    @Column
    private Timestamp limitDate;

    @Column
    private Timestamp createdDate;

    @Column
    private Timestamp updatedDate;
}
