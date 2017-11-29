package com.bh.timetracker.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.timetracker.entity.TaskType;

import com.bh.timetracker.exception.TaskTypeNotFoundException;

import com.bh.timetracker.service.TaskTypeService;
import com.bh.timetracker.utility.Payload;

@RestController
@RequestMapping("taskType")
public class TaskTypeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskTypeService taskTypeService;

	@GetMapping("getTaskTypeList")
	public ResponseEntity<Payload<List<TaskType>>> getTaskTypeList() {
		logger.info("getTaskTypeList request");
		List<TaskType> list = null;
		try {
			list = taskTypeService.getTaskTypeList();
		} catch (Exception e) {
			return new ResponseEntity<Payload<List<TaskType>>>(
					new Payload<List<TaskType>>(new TaskTypeNotFoundException("No TaskType found")),
					HttpStatus.NOT_FOUND);
		}
		logger.info("completed getTaskTypeList request");
		return new ResponseEntity<Payload<List<TaskType>>>(new Payload<List<TaskType>>(list), HttpStatus.OK);
	}
}
