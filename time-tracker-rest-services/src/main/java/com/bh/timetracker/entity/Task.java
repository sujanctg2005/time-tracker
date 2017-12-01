package com.bh.timetracker.entity;

import java.io.Serializable;
import javax.persistence.*;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the TASKS database table.
 * 
 */
@Entity
@Table(name = "TASKS")
@NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t")
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TASKS_TASKID_GENERATOR", sequenceName = "TASKS_TASK_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASKS_TASKID_GENERATOR")
	@Column(name = "TASK_ID")
	private long taskId;

	private String comments;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate= new Date();;

	@Column(name = "HOURS")
	private BigDecimal hours;

	@Temporal(TemporalType.DATE)
	@Column(name = "TASK_DATE")
	@JsonSerialize(using = CustomDataSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date taskDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE")
	private Date updateDate= new Date();;

	// bi-directional many-to-one association to Category
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	// bi-directional many-to-one association to TaskType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_TYPE_ID")
	private TaskType taskType;

	// bi-directional many-to-one association to Tiket
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ID")
	private Ticket ticket;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private User user;

	//bi-directional many-to-one association to Medium
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEDIUM_ID")
	private Medium medium;
	
	
	//bi-directional many-to-one association to Subtype
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUBTYPE_ID")
	private Subtype subtype;

	
	public Task() {
	}

	public long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getHours() {
		return this.hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	public Date getTaskDate() {
		return this.taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public TaskType getTaskType() {
		return this.taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Ticket getTiket() {
		return this.ticket;
	}

	public void setTiket(Ticket tiket) {
		this.ticket = tiket;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Medium getMedium() {
		return this.medium;
	}

	public void setMedium(Medium medium) {
		this.medium = medium;
	}
	public Subtype getSubtype() {
		return this.subtype;
	}

	public void setSubtype(Subtype subtype) {
		this.subtype = subtype;
	}
	
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", comments=" + comments + ", createDate=" + createDate + ", hours=" + hours
				+ ", taskDate=" + taskDate + ", updateDate=" + updateDate + ", category=" + category + ", taskType="
				+ taskType + ", tiket=" + ticket + ", user=" + user + "]";
	}

}