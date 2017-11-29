package com.bh.timetracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.dao.TaskTypeDao;

import com.bh.timetracker.entity.TaskType;

@Service
public class TaskTypeService {
	@Autowired
	private TaskTypeDao taskTypeDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<TaskType> getTaskTypeList() {
		logger.info("getting  TaskType list from  dao");
		List<TaskType> cl = taskTypeDao.getTaskTypeList();
		logger.info("got TaskType list from dao");
		return cl;
	}
}
