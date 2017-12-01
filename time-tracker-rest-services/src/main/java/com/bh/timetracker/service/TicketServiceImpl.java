package com.bh.timetracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bh.timetracker.entity.Ticket;

import com.bh.timetracker.dao.TicketDaoImpl;

@Service
public class TicketServiceImpl {

	@Autowired
	private TicketDaoImpl ticketDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Ticket getTicketOrAdd(Ticket ticket) {
		logger.info("getting  ticket info   from  dao");
		Ticket t = null;
		t = ticketDao.getTicker(ticket);
		if (t == null) {
			t = ticketDao.save(ticket);
		}
		logger.info("got ticket info  from dao");
		return t;
	}
}
