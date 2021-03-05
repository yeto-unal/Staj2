package com.example.test;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, UUID>{

	List<Task> findByNameContaining(String name);
}
