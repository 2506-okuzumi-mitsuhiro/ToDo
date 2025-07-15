package com.example.odango.repository;

import com.example.odango.repository.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Integer> {
    public List<Tasks> findByLimitDateBetweenAndContentAndStatus(Timestamp startDate, Timestamp endDate, String content, Short status);
    public List<Tasks> findByLimitDateBetweenAndStatus(Timestamp startDate, Timestamp endDate, String content);
    public List<Tasks> findByLimitDateBetweenAndContent(Timestamp startDate, Timestamp endDate, Short status);
    public List<Tasks> findByLimitDateBetween(Timestamp startDate, Timestamp endDate);
}