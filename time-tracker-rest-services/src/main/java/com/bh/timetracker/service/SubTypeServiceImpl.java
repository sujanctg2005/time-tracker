
package com.bh.timetracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.dao.SubTypeDao;

import com.bh.timetracker.entity.Subtype;

@Service
public class SubTypeServiceImpl {
	@Autowired
	private SubTypeDao subTypeDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Subtype> getSubTypeList() {
		logger.info("getting  Subtype list from  dao");
		List<Subtype> cl = subTypeDao.getSubTypeList();
		logger.info("got Subtype list from dao");
		return cl;
	}
}
