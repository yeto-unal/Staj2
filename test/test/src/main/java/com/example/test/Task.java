package com.example.test;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "task")
public class Task {
	
	@Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@Column(name = "name")
	private String name;
	
	@OneToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="type", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
	private TaskType type;
	
	@Column(name = "progress")
	private int progress;
	
	@OneToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="status", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
	private TaskStatus status;
	
	@Column(name = "starttime")
	Date starttime = new Date();
	
	@Column(name = "endtime")
	Date endtime = new Date();
	
	@Column(name = "updatetime")
	Date updatetime = new Date();
	
	
	public Task(UUID id, String name, TaskType type, int progress, TaskStatus status, Date starttime, Date endtime, Date updatetime) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.progress = progress;
		this.status = status;
		this.starttime = starttime;
		this.endtime = endtime;
		this.updatetime = updatetime;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public TaskType getType() {
		return type;
	}


	public void setType(TaskType type) {
		this.type = type;
	}


	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		this.progress = progress;
	}


	public Task(String name, TaskType type, int progress, TaskStatus status, Date starttime, Date endtime, Date updatetime) {
		super();
		this.name = name;
		this.type = type;
		this.progress = progress;
		this.status = status;
		this.starttime = starttime;
		this.endtime = endtime;
		this.updatetime = updatetime;
	}


	public TaskStatus getStatus() {
		return status;
	}


	public void setStatus(TaskStatus status) {
		this.status = status;
	}


	public Date getStarttime() {
		return starttime;
	}


	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}


	public Date getEndtime() {
		return endtime;
	}


	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


	public Date getUpdatetime() {
		return updatetime;
	}


	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}


	public Task() {
		
	}
	
	
}
