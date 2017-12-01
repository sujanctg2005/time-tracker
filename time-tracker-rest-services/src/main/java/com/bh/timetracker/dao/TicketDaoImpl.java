package com.bh.timetracker.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bh.timetracker.entity.Ticket;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class TicketDaoImpl {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Ticket getTicker(Ticket Ticket) {
		logger.info("executing the query IncidentId:" + Ticket.getIncidentId());
		String sql = "FROM Ticket AS TIC WHERE TIC.incidentId=:incidentId and TIC.categoryName=:categoryName";
		Ticket t = null;
		try {
			t = entityManager.createQuery(sql, Ticket.class).setParameter("incidentId", Ticket.getIncidentId()).setParameter("categoryName", Ticket.getCategoryName()).getSingleResult();
		} catch (Exception e) {
			logger.info("inciden not found");
		}

		logger.info("completed the  query");
		return t;
	}

	public Ticket save(Ticket Ticket) {
		logger.info("adding ticket in db ");
		entityManager.persist(Ticket);

		logger.info("completed adding ticket in db");
		return Ticket;
	}
}
