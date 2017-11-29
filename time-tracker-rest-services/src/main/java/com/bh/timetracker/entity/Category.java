package com.bh.timetracker.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CATEGORY database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORY_CATEGORYID_GENERATOR", sequenceName="CATEGORY_CATEGORY_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_CATEGORYID_GENERATOR")
	@Column(name="CATEGORY_ID")
	private long categoryId;

	@Column(name="CATEGORY_NAME")
	private String categoryName;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="category")
	private List<Task> tasks;

	public Category() {
	}

	public long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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
		task.setCategory(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setCategory(null);

		return task;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", tasks=" + tasks + "]";
	}

}