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

import com.bh.timetracker.entity.Subtype;
import com.bh.timetracker.exception.SubTypeException;
import com.bh.timetracker.service.SubTypeServiceImpl;

import com.bh.timetracker.utility.Payload;

@RestController
@RequestMapping("subtype")
public class SubTypeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SubTypeServiceImpl subTypeServiceImpl;

	@GetMapping("getSubtypeList")
	public ResponseEntity<Payload<List<Subtype>>> getSubtypeList() {
		logger.info("getSubtypeList request");
		List<Subtype> list = null;
		try {
			list = subTypeServiceImpl.getSubTypeList();
		} catch (Exception e) {
			return new ResponseEntity<Payload<List<Subtype>>>(
					new Payload<List<Subtype>>(new SubTypeException("No Subtype found")),
					HttpStatus.NOT_FOUND);
		}
		logger.info("completed getSubtypeList request");
		return new ResponseEntity<Payload<List<Subtype>>>(new Payload<List<Subtype>>(list), HttpStatus.OK);
	}
}
