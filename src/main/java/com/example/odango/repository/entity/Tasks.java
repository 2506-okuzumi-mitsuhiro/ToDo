package com.example.odango.repository.entity;

<<<<<<< HEAD
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

=======
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


>>>>>>> b5596057f9807704bf3a6b5b3ab63b6c250be5d9
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Tasks {
    @Id
    @Column
<<<<<<< HEAD
=======
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> b5596057f9807704bf3a6b5b3ab63b6c250be5d9
    private int id;

    @Column
    private String content;

    @Column
    private short status;

<<<<<<< HEAD
    @Column
    private Timestamp limitDate;

    @Column
    private Timestamp createdDate;

    @Column
=======
    @Column(name = "limit_date")
    private Timestamp limitDate;

    // limitDateを文字列で取得するために追加
    @Transient
    private String strLimitDate;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", insertable = false)
>>>>>>> b5596057f9807704bf3a6b5b3ab63b6c250be5d9
    private Timestamp updatedDate;
}