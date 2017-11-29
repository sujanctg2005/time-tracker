package com.bh.timetracker.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TIKET database table.
 * 
 */
@Entity
@NamedQuery(name="Tiket.findAll", query="SELECT t FROM Tiket t")
public class Tiket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIKET_TIKETID_GENERATOR", sequenceName="TIKET_TIKET_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIKET_TIKETID_GENERATOR")
	@Column(name="TIKET_ID")
	private long tiketId;

	@Column(name="ASSIGNED_GROUP")
	private String assignedGroup;

	@Column(name="CATEGORY_NAME")
	private String categoryName;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate = new Date();

	@Column(name="INCIDENT_ID")
	private String incidentId;

	@Column(name="TIKET_DESCRIPTION")
	private String tiketDescription;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate = new Date();

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="tiket")
	private List<Task> tasks;

	public Tiket() {
	}

	public long getTiketId() {
		return this.tiketId;
	}

	public void setTiketId(long tiketId) {
		this.tiketId = tiketId;
	}

	public String getAssignedGroup() {
		return this.assignedGroup;
	}

	public void setAssignedGroup(String assignedGroup) {
		this.assignedGroup = assignedGroup;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIncidentId() {
		return this.incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getTiketDescription() {
		return this.tiketDescription;
	}

	public void setTiketDescription(String tiketDescription) {
		this.tiketDescription = tiketDescription;
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
		task.setTiket(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setTiket(null);

		return task;
	}

	@Override
	public String toString() {
		return "Tiket [tiketId=" + tiketId + ", assignedGroup=" + assignedGroup + ", categoryName=" + categoryName
				+ ", createDate=" + createDate + ", incidentId=" + incidentId + ", tiketDescription=" + tiketDescription
				+ ", updateDate=" + updateDate + ", tasks=" + tasks + "]";
	}

}