package com.example.odango.repository;

import com.example.odango.repository.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Integer> {
    public List<Tasks> findAllByOrderByLimitDateAsc();
    public List<Tasks> findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc(Timestamp startDate, Timestamp endDate, String content, Short status);
    public List<Tasks> findByLimitDateBetweenAndContentOrderByLimitDateAsc(Timestamp startDate, Timestamp endDate, String content);
    public List<Tasks> findByLimitDateBetweenAndStatusOrderByLimitDateAsc(Timestamp startDate, Timestamp endDate, Short status);
    public List<Tasks> findByLimitDateBetweenOrderByLimitDateAsc(Timestamp startDate, Timestamp endDate);
}