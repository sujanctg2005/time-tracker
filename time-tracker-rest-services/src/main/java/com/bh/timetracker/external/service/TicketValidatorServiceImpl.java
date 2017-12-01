package com.bh.timetracker.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.external.dao.TicketValidatorDao;


@Service
public class TicketValidatorServiceImpl {

	@Autowired
	private TicketValidatorDao ticketValidatorDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String[] getTicketInfo(String ticketTableInfo, String ticketId) {
		logger.info("getting  ticket info   from  dao");
		String[] t = null;
		t = ticketValidatorDao.getTicketInfo(ticketTableInfo, ticketId);

		logger.info("got ticket info  from dao");
		return t;
	}
}
