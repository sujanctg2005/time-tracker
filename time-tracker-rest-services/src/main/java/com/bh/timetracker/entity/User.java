package com.bh.timetracker.entity;
import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_USERNAME_GENERATOR", sequenceName="USERS_USERNAME_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_USERNAME_GENERATOR")
	private String username;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	private String designation;

	private String name;

	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="user")
	private List<Task> tasks;

	//bi-directional many-to-one association to UserGroup
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_GROUP_ID")
	private UserGroup userGroup;

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		task.setUser(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setUser(null);

		return task;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", createDate=" + createDate + ", designation=" + designation + ", name="
				+ name + ", password=" + password + ", updateDate=" + updateDate + ", tasks=" + tasks + ", userGroup="
				+ userGroup + "]";
	}

}