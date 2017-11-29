package com.bh.timetracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.dao.UserDaoImpl;

import com.bh.timetracker.entity.User;

@Service
public class UserServiceImpl {
	@Autowired
	private UserDaoImpl userDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public User getUser(User user) {
		logger.info("getting  user   from  dao");
		User u = userDao.getUser(user);
		logger.info("got user   from  dao");
		return u;
	}
}
