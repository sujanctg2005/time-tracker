package com.bh.timetracker.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import com.bh.timetracker.entity.Category;
import com.bh.timetracker.entity.Task;
import com.bh.timetracker.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class CategoryDaoImpl {
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

	

}
