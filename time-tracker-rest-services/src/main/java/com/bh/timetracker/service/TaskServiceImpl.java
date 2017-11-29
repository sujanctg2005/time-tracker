package com.bh.timetracker.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.dao.TaskDaoImpl;

import com.bh.timetracker.entity.Task;
import com.bh.timetracker.entity.User;

@Service
public class TaskServiceImpl {
	@Autowired
	private TaskDaoImpl taskDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void saveTask(Task task) {
		logger.info("calling save  dao, taskid" + task.getTaskId());
		taskDao.saveTask(task);
		logger.info("completed save  task ");
	}

	public void deleteTask(Task task) {
		logger.info("calling delete  dao, taskid" + task.getTaskId());
		taskDao.deleteTask(task);
		logger.info("completed delete  task ");
	}

	public void updateTask(Task updatedTask) {
		logger.info("calling update  dao, taskid" + updatedTask.getTaskId());
		taskDao.updateTask(updatedTask);
		logger.info("completed update  task ");
	}

	public List<Task> getTaskList(User user, Date date) {
		logger.info("getting  user task  list from  dao" + user.getName());
		List<Task> list = taskDao.getTaskList(user,date);
		logger.info("got task list from dao");
		return list;
	}
	
	public List<Task> getTaskListV1(User user, Date date) {
		logger.info("getting  user task  list from  dao" + user.getName());
		List<Task> list = taskDao.getTaskListV1(user,date);
		logger.info("got task list from dao");
		return list;
	}
	

	public Task getTask(Task t) {
		logger.info("getting  user task   from  dao");
		Task task = taskDao.getTask(t);
		logger.info("got task  from dao");
		return task;
	}
}
