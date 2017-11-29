package com.bh.timetracker.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SUBTYPE database table.
 * 
 */
@Entity
@NamedQuery(name="Subtype.findAll", query="SELECT s FROM Subtype s")
public class Subtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SUBTYPE_SUBTYPEID_GENERATOR", sequenceName="SUBTYPE_SUBTYPE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUBTYPE_SUBTYPEID_GENERATOR")
	@Column(name="SUBTYPE_ID")
	private long subtypeId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="SUBTYPE_NAME")
	private String subtypeName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="subtype")
	private List<Task> tasks;

	public Subtype() {
	}

	public long getSubtypeId() {
		return this.subtypeId;
	}

	public void setSubtypeId(long subtypeId) {
		this.subtypeId = subtypeId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSubtypeName() {
		return this.subtypeName;
	}

	public void setSubtypeName(String subtypeName) {
		this.subtypeName = subtypeName;
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
		task.setSubtype(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setSubtype(null);

		return task;
	}

}