package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TaskStatusController {
	
	@Autowired
	TaskStatusRepo taskStatusRepo;
	
	@GetMapping("/taskstatus")
	public ResponseEntity<List<TaskStatus>> getAllTaskStatus(@RequestParam(required = false) String name){
		try {
			List<TaskStatus> statuses = new ArrayList<TaskStatus>();
			if(name == null)
				taskStatusRepo.findAll().forEach(statuses::add);
			else
				taskStatusRepo.findByNameContaining(name).forEach(statuses::add);
			
			if(statuses.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(statuses, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/taskstatus/{id}")
	public ResponseEntity<TaskStatus> getTaskById(@PathVariable("id") UUID id) {
		Optional<TaskStatus> taskStatusData = taskStatusRepo.findById(id);

		if (taskStatusData.isPresent()) {
			return new ResponseEntity<>(taskStatusData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/taskstatus")
	public ResponseEntity<TaskStatus> createTaskStatus(@RequestBody TaskStatus taskStatus){
		try {
			TaskStatus _taskStatus = taskStatusRepo.save(new TaskStatus(taskStatus.getId(), taskStatus.getName()));
			return new ResponseEntity<>(_taskStatus, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/taskstatus/{id}")
	public ResponseEntity<TaskStatus> updateTaskStatus(@PathVariable("id") UUID id, @RequestBody TaskStatus taskStatus){
		Optional<TaskStatus> taskStatusData = taskStatusRepo.findById(id);
		
		if(taskStatusData.isPresent()) {
			TaskStatus _taskStatus = taskStatusData.get();
			_taskStatus.setId(taskStatus.getId());
			_taskStatus.setName(taskStatus.getName());
			return new ResponseEntity<>(taskStatusRepo.save(_taskStatus), HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/taskstatus/{id}")
	public ResponseEntity<HttpStatus> deleteTaskStatus(@PathVariable("id") UUID id){
		try {
			taskStatusRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/taskstatus")
	public ResponseEntity<HttpStatus> deleteAllTaskStatus(){
		try {
			taskStatusRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
