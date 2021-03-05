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
public class TaskTypeController {
	
	@Autowired
	TaskTypeRepo taskTypeRepo;
	
	@GetMapping("/tasktype")
	public ResponseEntity<List<TaskType>> getAllTaskType(@RequestParam(required = false) String name){
		try {
			List<TaskType> types = new ArrayList<TaskType>();
			if(name == null)
				taskTypeRepo.findAll().forEach(types::add);
			else
				taskTypeRepo.findByNameContaining(name).forEach(types::add);
			
			if(types.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(types, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tasktype/{id}")
	public ResponseEntity<TaskType> getTaskById(@PathVariable("id") UUID id) {
		Optional<TaskType> taskTypeData = taskTypeRepo.findById(id);

		if (taskTypeData.isPresent()) {
			return new ResponseEntity<>(taskTypeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/tasktype")
	public ResponseEntity<TaskType> createTaskType(@RequestBody TaskType taskType){
		try {
			TaskType _taskType = taskTypeRepo.save(new TaskType(taskType.getId(), taskType.getName()));
			return new ResponseEntity<>(_taskType, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/tasktype/{id}")
	public ResponseEntity<TaskType> updateTaskType(@PathVariable("id") UUID id, @RequestBody TaskType taskType){
		Optional<TaskType> taskTypeData = taskTypeRepo.findById(id);
		
		if(taskTypeData.isPresent()) {
			TaskType _taskType = taskTypeData.get();
			_taskType.setId(taskType.getId());
			_taskType.setName(taskType.getName());
			return new ResponseEntity<>(taskTypeRepo.save(_taskType), HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/tasktype/{id}")
	public ResponseEntity<HttpStatus> deleteTaskType(@PathVariable("id") UUID id){
		try {
			taskTypeRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tasktype")
	public ResponseEntity<HttpStatus> deleteAllTaskType(){
		try {
			taskTypeRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
