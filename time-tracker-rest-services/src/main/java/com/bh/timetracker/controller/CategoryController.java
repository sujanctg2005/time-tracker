package com.bh.timetracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.bh.timetracker.entity.Category;
import com.bh.timetracker.exception.CategoryNotFoundException;
import com.bh.timetracker.service.TaskCategoryServiceImpl;
import com.bh.timetracker.utility.Payload;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("category")
public class CategoryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskCategoryServiceImpl taskCategoryService;

	@GetMapping("getCategoryList")
	public ResponseEntity<Payload<List<Category>>> getCategoryList()  {
		logger.info("getCategoryList request");
		List<Category> list = null;
		try {
			list = taskCategoryService.getCategoryList();
		} catch (Exception e) {
			return new ResponseEntity<Payload<List<Category>>>(
					new Payload<List<Category>>(new CategoryNotFoundException("No category found")), HttpStatus.NOT_FOUND);
		}
		logger.info("completed getCategoryList request");
		return new ResponseEntity<Payload<List<Category>>>(new Payload<List<Category>>(list), HttpStatus.OK);
	}
}
