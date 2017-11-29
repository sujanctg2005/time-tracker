package com.bh.timetracker.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bh.timetracker.entity.Tiket;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class TicketDaoImpl {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Tiket getTicker(Tiket tiket) {
		logger.info("executing the query IncidentId:" + tiket.getIncidentId());
		String sql = "FROM Tiket AS TIC WHERE TIC.incidentId=:incidentId and TIC.categoryName=:categoryName";
		Tiket t = null;
		try {
			t = entityManager.createQuery(sql, Tiket.class).setParameter("incidentId", tiket.getIncidentId()).setParameter("categoryName", tiket.getCategoryName()).getSingleResult();
		} catch (Exception e) {
			logger.info("inciden not found");
		}

		logger.info("completed the  query");
		return t;
	}

	public Tiket save(Tiket tiket) {
		logger.info("adding ticket in db ");
		entityManager.persist(tiket);

		logger.info("completed adding ticket in db");
		return tiket;
	}
}
