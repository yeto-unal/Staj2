package com.example.test;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepo extends JpaRepository<TaskStatus, UUID>{

	List<TaskStatus> findByNameContaining(String name);
}
