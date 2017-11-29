
package com.bh.timetracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.bh.timetracker.entity.Medium;

import com.bh.timetracker.exception.MediumException;
import com.bh.timetracker.service.MediumServiceImpl;

import com.bh.timetracker.utility.Payload;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("medium")
public class MediumController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MediumServiceImpl mediumService;

	@GetMapping("getMediumList")
	public ResponseEntity<Payload<List<Medium>>> getMediumList() {
		logger.info("getMediumList request");
		List<Medium> list = null;
		try {
			list = mediumService.getMediumList();
		} catch (Exception e) {
			return new ResponseEntity<Payload<List<Medium>>>(
					new Payload<List<Medium>>(new MediumException("No Medium found")), HttpStatus.NOT_FOUND);
		}
		logger.info("completed getMediumList request");
		return new ResponseEntity<Payload<List<Medium>>>(new Payload<List<Medium>>(list), HttpStatus.OK);
	}
}
