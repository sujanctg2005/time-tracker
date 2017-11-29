package com.bh.timetracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.timetracker.dao.CategoryDaoImpl;
import com.bh.timetracker.entity.Category;

@Service
public class TaskCategoryServiceImpl {
	@Autowired
	private CategoryDaoImpl categoryDaoImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Category> getCategoryList() {
		logger.info("getting  category list from  dao");
		List<Category> cl = categoryDaoImpl.getCategoryList();
		logger.info("got category list from dao");
		return cl;
	}
}
