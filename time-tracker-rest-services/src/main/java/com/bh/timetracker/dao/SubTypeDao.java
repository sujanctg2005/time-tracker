package com.bh.timetracker.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bh.timetracker.entity.Subtype;


@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class SubTypeDao {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Subtype> getSubTypeList() {
		logger.info("executing the query");
		String sql = "FROM Subtype AS tt";
		List<Subtype> list = entityManager.createQuery(sql, Subtype.class).getResultList();
		logger.info("completed the  query");
		return list;
	}
}
