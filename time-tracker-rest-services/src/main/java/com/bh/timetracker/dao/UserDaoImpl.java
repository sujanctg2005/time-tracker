package com.bh.timetracker.dao;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bh.timetracker.entity.User;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class UserDaoImpl {
	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public User getUser(User user) {
		logger.info("loading user");
		String sql = "FROM User AS u WHERE  u.username=:username and  u.password=:password ";
		User u = entityManager.createQuery(sql, User.class).setParameter("username", user.getUsername())
				.setParameter("password", user.getPassword()).getSingleResult();
		logger.info("completed loading user");
		return u;
	}
}
