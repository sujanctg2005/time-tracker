package com.bh.timetracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bh.timetracker.exception.TaskException;
import com.bh.timetracker.external.service.TicketValidatorServiceImpl;
import com.bh.timetracker.utility.Payload;


@RestController
@RequestMapping("ticket")
public class TicketValidatorController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	TicketValidatorServiceImpl ticketValidatorServiceImpl;

	@GetMapping("/validateTicket/{tableinfo}/{ticketId}")
	public ResponseEntity<Payload<String[]>> validateTicket(@PathVariable("tableinfo") String tableinfo,
			@PathVariable("ticketId") String ticketId) {
		logger.info("validateTicket request");

		/* for response payload */
		Payload<String[]> payload = null;
		ResponseEntity<Payload<String[]>> responseEntity = null;
		/* end */
		try {
			String [] data=ticketValidatorServiceImpl.getTicketInfo(tableinfo, ticketId);
			
			/* for void response body */
			payload = new Payload<String[]>(data);
			responseEntity = new ResponseEntity<Payload<String[]>>(payload,  HttpStatus.OK);
			logger.info("completed validateTicket request");
			
			return responseEntity;
			/* end */

		} catch (Exception e) {
			logger.error("fail to validateTicket", e);
			payload = new Payload<String[]>(new TaskException("fail to validateTicket"));
			responseEntity = new ResponseEntity<Payload<String[]>>(payload, HttpStatus.BAD_REQUEST);
			return responseEntity;
		}
	}
}
