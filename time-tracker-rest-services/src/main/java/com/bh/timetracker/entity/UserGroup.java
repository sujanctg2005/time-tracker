package com.bh.timetracker.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USER_GROUP database table.
 * 
 */
@Entity
@Table(name="USER_GROUP")
@NamedQuery(name="UserGroup.findAll", query="SELECT u FROM UserGroup u")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_GROUP_USERGROUPID_GENERATOR", sequenceName="USER_GROUP_USER_GROUP_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_GROUP_USERGROUPID_GENERATOR")
	@Column(name="USER_GROUP_ID")
	private long userGroupId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="USER_GROUP_NAME")
	private String userGroupName;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userGroup")
	private List<User> users;

	public UserGroup() {
	}

	public long getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
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

	public String getUserGroupName() {
		return this.userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserGroup(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserGroup(null);

		return user;
	}

}