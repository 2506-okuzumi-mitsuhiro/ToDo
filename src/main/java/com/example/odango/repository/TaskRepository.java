package com.example.odango.repository;

import org.springframework.scheduling.config.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
