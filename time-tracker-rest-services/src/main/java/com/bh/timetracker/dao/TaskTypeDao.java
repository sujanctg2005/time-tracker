package com.bh.timetracker.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bh.timetracker.entity.TaskType;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class TaskTypeDao {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<TaskType> getTaskTypeList() {
		logger.info("executing the query");
		String sql = "FROM TaskType AS tt";
		List<TaskType> list = entityManager.createQuery(sql, TaskType.class).getResultList();
		logger.info("completed the  query");
		return list;
	}
}
