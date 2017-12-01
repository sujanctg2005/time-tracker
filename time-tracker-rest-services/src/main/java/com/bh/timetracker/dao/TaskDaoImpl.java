package com.bh.timetracker.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import com.bh.timetracker.entity.Category;
import com.bh.timetracker.entity.Medium;
import com.bh.timetracker.entity.Task;
import com.bh.timetracker.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class TaskDaoImpl {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Category> getCategoryList() {
		logger.info("executing the query");
		String sql = "FROM Category AS CATG";
		List<Category> list = entityManager.createQuery(sql, Category.class).getResultList();
		logger.info("completed the  query");
		return list;
	}

	public void saveTask(Task task) {
		logger.info("saving the task");
		entityManager.persist(task);
		logger.info("completed saving the task");

	}

	public void deleteTask(Task task) {
		logger.info("delete the task");
		Task t=entityManager.find(Task.class, task.getTaskId());
		entityManager.remove(t);
		logger.info("completed delete the task");

	}

	public void updateTask(Task updatedTask) {
		logger.info("saving the task");
		Task task = entityManager.find(Task.class, updatedTask.getTaskId());

		task.setCategory(updatedTask.getCategory());
		task.setTaskType(updatedTask.getTaskType());
		task.setTiket(updatedTask.getTiket());
		task.setUpdateDate(new Date());
		task.setHours(updatedTask.getHours());
		entityManager.merge(task);

		logger.info("completed saving the task");
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
	public List<Task> getTaskList(User user,Date date) {
		logger.info("executing the query to load all task for user " + user.getName());
		String sql = "FROM Task AS T WHERE  T.user.username=:username and T.taskDate=:taskDate order by taskId desc";
		List<Task> list = entityManager.createQuery(sql, Task.class).setParameter("username", user.getUsername()).setParameter("taskDate", date).getResultList();
		for (Task t : list) {
			// lazy data load
			t.getCategory().getCategoryName();
			if(t.getTiket()!=null) {
				t.getTiket().getTiketDescription();
			}
			
			t.getUser().getDesignation();
			t.getUser().setPassword(null);
			t.getTaskType().getTaskTypeName();
		}
		logger.info("completed the  query to load all task");
		return list;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
	public List<Task> getTaskListV1(User user,Date date) {
		logger.info("executing the query to load all task for user " + user.getName());
		String sql = "SELECT t FROM Task t JOIN FETCH t.category c JOIN FETCH t.taskType tp  LEFT JOIN  FETCH  t.ticket tk "
				+ " JOIN FETCH t.user u JOIN FETCH t.medium m "
				+ " WHERE  t.user.username=:username "
				+ " and t.taskDate=:taskDate order by t.taskId desc";
		List<Task> list = entityManager.createQuery(sql, Task.class).setParameter("username", user.getUsername()).setParameter("taskDate", date).getResultList();
		logger.info("completed the  query to load all task");
		return list;
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
	public Task getTask(Task task) {
		logger.info("loading task info");
		Task t = entityManager.find(Task.class, task.getTaskId());
		// lazy data load
		t.getCategory().getCategoryName();
		t.getTiket().getTiketDescription();
		t.getUser().getDesignation();
		t.getUser().setPassword(null);
		t.getTaskType().getTaskTypeName();
		logger.info("completed oading task info");
		return t;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
	public Task getTaskV1(Task task) {
		logger.info("loading task info");
		String sql="SELECT t FROM Task t JOIN FETCH t.Category c JOIN FETCH t.TaskType tp  JOIN FETCH  t.Tiket tk JOIN FETCH t.User u  WHERE t.taskId=:taskId";		
		Task t = entityManager.createQuery(sql, Task.class).setParameter("taskId", task.getTaskId()).getSingleResult();
		logger.info("completed oading task info");
		return t;
	}
	

}
