package com.example.odango.repository;

import com.example.odango.repository.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Integer> {
    public List<Tasks> findAllByOrderByLimitDateAsc();
}