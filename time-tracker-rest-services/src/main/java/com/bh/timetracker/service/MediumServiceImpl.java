package com.bh.timetracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.dao.MediumDao;

import com.bh.timetracker.entity.Medium;

@Service
public class MediumServiceImpl {
	@Autowired
	private MediumDao mediumDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Medium> getMediumList() {
		logger.info("getting  Medium list from  dao");
		List<Medium> cl = mediumDao.getMediumList();
		logger.info("got Medium list from dao");
		return cl;
	}
}
