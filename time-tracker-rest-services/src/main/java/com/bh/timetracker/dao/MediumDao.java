package com.bh.timetracker.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.bh.timetracker.entity.Medium;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class MediumDao {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Medium> getMediumList() {
		logger.info("executing the query");
		String sql = "FROM Medium AS m";
		List<Medium> list = entityManager.createQuery(sql, Medium.class).getResultList();
		logger.info("completed the  query");
		return list;
	}

	

}
