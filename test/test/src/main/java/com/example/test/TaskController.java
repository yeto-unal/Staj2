package com.example.test;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
public class TaskController {

	@Autowired
	TaskStatusRepo taskStatusRepo;
	
	@Autowired
	TaskRepo taskRepo;
	
	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false) String name) {
		
		  try { List<Task> tasks = new ArrayList<Task>();
		  
		  	 if(name == null) 
		  		 taskRepo.findAll().forEach(tasks::add);	
		  	 else
		  		 taskRepo.findByNameContaining(name).forEach(tasks::add);
		  
		  	 for(int i = 0; i < tasks.size(); i++) {
		  		 if(tasks.get(i).getStatus().getId().equals(UUID.fromString(taskStatusRepo.findByNameContaining("deleted").get(0).getId().toString())))
		  			 tasks.remove(i);
		  	 }
		  
		  if(tasks.isEmpty()) {
			  return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		  }
		  return new ResponseEntity<>(tasks, HttpStatus.OK); 
		  }catch (Exception e) {
			  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
		  }
		 
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("id") UUID id) {
		Optional<Task> taskData = taskRepo.findById(id);

		if (taskData.isPresent()) {
			return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/tasks")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		Date cDate = new Date();
		try {
			Task _task = taskRepo.save(new Task(task.getName(), task.getType(), task.getProgress(), task.getStatus(),
					cDate, null, null));
			return new ResponseEntity<>(_task, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") UUID id, @RequestBody Task task) {
		Optional<Task> taskData = taskRepo.findById(id);
		Date cDate = new Date();

		if (taskData.isPresent()) {
			Task _task = taskData.get();
			_task.setName(task.getName());
			_task.setType(task.getType());
			_task.setProgress(task.getProgress());
			_task.setStatus(task.getStatus());
			_task.setUpdatetime(cDate);
			if(task.getStatus().getId().equals(UUID.fromString(taskStatusRepo.findByNameContaining("done").get(0).getId().toString())))
				_task.setEndtime(cDate);
			return new ResponseEntity<>(taskRepo.save(_task), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping("/tasks/{id}")
	public ResponseEntity<Task> modifyTask(@PathVariable("id") UUID id, @RequestBody Task task){
		Optional<Task> taskData = taskRepo.findById(id);
		Date cDate = new Date();
		
		if(taskData.isPresent()) {
			Task _task = taskData.get();
			if(task.getName() != null)
				_task.setName(task.getName());
			if(task.getType() != null)
				_task.setType(task.getType());
			if(task.getProgress() != 0)
				_task.setProgress(task.getProgress());
			if(task.getStatus() != null)
				_task.setStatus(task.getStatus());
			_task.setUpdatetime(cDate);
			return new ResponseEntity<>(taskRepo.save(_task), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<Task> deleteTask(@PathVariable("id") UUID id) {
		Optional<Task> taskData = taskRepo.findById(id);
			
		UUID del = UUID.fromString(taskStatusRepo.findByNameContaining("deleted").get(0).getId().toString());
		String name = "deleted";
		TaskStatus tStatus = new TaskStatus(del, name);
		Date cDate = new Date();
		
		try {
			Task _task = taskData.get();
			_task.setStatus(tStatus);
			_task.setUpdatetime(cDate);
			return new ResponseEntity<>(taskRepo.save(_task), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

	/*@DeleteMapping("/tasks")
	public ResponseEntity<HttpStatus> deleteAllTasks() {
		try {
			taskRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
}