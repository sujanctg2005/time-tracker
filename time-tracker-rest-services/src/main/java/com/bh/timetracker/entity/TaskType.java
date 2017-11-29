package com.bh.timetracker.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TASK_TYPE database table.
 * 
 */
@Entity
@Table(name="TASK_TYPE")
@NamedQuery(name="TaskType.findAll", query="SELECT t FROM TaskType t")
public class TaskType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TASK_TYPE_TASKTYPEID_GENERATOR", sequenceName="TASK_TYPE_TASK_TYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TASK_TYPE_TASKTYPEID_GENERATOR")
	@Column(name="TASK_TYPE_ID")
	private long taskTypeId;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone="GMT-8")
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="TASK_TYPE_NAME")
	private String taskTypeName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="taskType")
	private List<Task> tasks;

	public TaskType() {
	}

	public long getTaskTypeId() {
		return this.taskTypeId;
	}

	public void setTaskTypeId(long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTaskTypeName() {
		return this.taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setTaskType(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setTaskType(null);

		return task;
	}

	@Override
	public String toString() {
		return "TaskType [taskTypeId=" + taskTypeId + ", createDate=" + createDate + ", taskTypeName=" + taskTypeName
				+ ", updateDate=" + updateDate + ", tasks=" + tasks + "]";
	}

}