package com.bh.timetracker.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the MEDIUM database table.
 * 
 */
@Entity
@NamedQuery(name="Medium.findAll", query="SELECT m FROM Medium m")
public class Medium implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MEDIUM_MEDIUMID_GENERATOR", sequenceName="MEDIUM_MEDIUM_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEDIUM_MEDIUMID_GENERATOR")
	@Column(name="MEDIUM_ID")
	private long mediumId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="MEDIUM_NAME")
	private String mediumName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="medium")
	private List<Task> tasks;

	public Medium() {
	}

	public long getMediumId() {
		return this.mediumId;
	}

	public void setMediumId(long mediumId) {
		this.mediumId = mediumId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMediumName() {
		return this.mediumName;
	}

	public void setMediumName(String mediumName) {
		this.mediumName = mediumName;
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
		task.setMedium(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setMedium(null);

		return task;
	}

}