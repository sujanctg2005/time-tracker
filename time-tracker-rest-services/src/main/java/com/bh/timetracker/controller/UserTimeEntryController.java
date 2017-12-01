package com.bh.timetracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.bh.timetracker.entity.CustomDataSerializer;
import com.bh.timetracker.entity.Task;
import com.bh.timetracker.entity.Ticket;
import com.bh.timetracker.entity.User;
import com.bh.timetracker.exception.TaskException;
import com.bh.timetracker.service.TaskServiceImpl;
import com.bh.timetracker.service.TicketServiceImpl;
import com.bh.timetracker.utility.Payload;
import com.bh.timetracker.utility.Void;

@Controller
@RequestMapping("timeEntry")
public class UserTimeEntryController {
	@Autowired
	private TaskServiceImpl taskService;
	@Autowired
	private UtilityComponet utilityComponet;
	
	@Autowired
	private TicketServiceImpl ticketService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@PostMapping("createTask")
	public ResponseEntity<Payload<Void>> createTask(@RequestBody Task task, UriComponentsBuilder builder) {
		logger.info("started createTask request\n" +task.toString() );
		
		/* for response payload */
		Payload<Void> payload = null;
		ResponseEntity<Payload<Void>> responseEntity = null;
		/* end */
		try {
			
			Ticket t=task.getTiket();
			if( !( t.getIncidentId()==null ||  t.getIncidentId().equals(""))) {
				t.setCategoryName(task.getCategory().getCategoryName());
				t.setTiketDescription(" ");			
				t=ticketService.getTicketOrAdd(t);
				
				task.setTiket(t);
			} else {
				task.setTiket(null);
			}
			
			
			
			//task.setUser(utilityComponet.getLoginUser());
			
			
			taskService.saveTask(task);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/getTask/{taskId}").buildAndExpand(task.getTaskId()).toUri());

			/* for void response body */
			payload = new Payload<Void>(new Void());
			responseEntity = new ResponseEntity<Payload<Void>>(payload, headers, HttpStatus.CREATED);
			logger.info("completed createTask request");
			
			return responseEntity;
			/* end */

		} catch (Exception e) {
			payload = new Payload<Void>(new TaskException("fail to create task"));
			responseEntity = new ResponseEntity<Payload<Void>>(payload, HttpStatus.CONFLICT);
			return responseEntity;
		}

	}

	@PostMapping("updateTask")
	public ResponseEntity<Payload<Void>> updateTask(@RequestBody Task task, UriComponentsBuilder builder) {

		logger.info("started updateTask request");
	
		
		/* for response payload */
		Payload<Void> payload = null;
		ResponseEntity<Payload<Void>> responseEntity = null;
		/* end */

		try {
			taskService.updateTask(task);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/getTask/{taskId}").buildAndExpand(task.getTaskId()).toUri());

			/* for void response body */
			payload = new Payload<Void>(new Void());
			responseEntity = new ResponseEntity<Payload<Void>>(payload, headers, HttpStatus.CREATED);
			logger.info("completed updateTask request");
			return responseEntity;
			/* end */

		} catch (Exception e) {
			payload = new Payload<Void>(new TaskException("fail to update task"));
			responseEntity = new ResponseEntity<Payload<Void>>(payload, HttpStatus.CONFLICT);
			return responseEntity;
		}

	}

	
	@DeleteMapping("/deleteTask/{taskId}")	
	public ResponseEntity<Payload<Void>> deleteTask(@PathVariable("taskId") Long taskId) {
		logger.info("started deleteTask request");
		
		/* for response payload */
		Payload<Void> payload = null;
		ResponseEntity<Payload<Void>> responseEntity = null;
		/* end */

		try {
			Task task = new Task();
			task.setTaskId(taskId);
			taskService.deleteTask(task);

			/* for void response body */
			payload = new Payload<Void>(new Void());
			responseEntity = new ResponseEntity<Payload<Void>>(payload, HttpStatus.OK);
			
			logger.info("completed deleteTask request");
			
			return responseEntity;
			/* end */

		} catch (Exception e) {
			logger.error("Fail to delete the task", e);
			payload = new Payload<Void>(new TaskException("Fail to delete the task"));
			responseEntity = new ResponseEntity<Payload<Void>>(payload, HttpStatus.NOT_FOUND);
			return responseEntity;
		}

	}
	
	
	@GetMapping("/allTasksByUserV1/{taskDate}/{userName}")
	public ResponseEntity<Payload<List<Task>>> allTasksByUserV1(@PathVariable("taskDate") String taskDate,@PathVariable("userName") String userName,HttpSession session) {
		logger.info("started allTasksByUser request");
		
		
		/* for response payload */
		Payload<List<Task>> payload = null;
		ResponseEntity<Payload<List<Task>>> responseEntity = null;
		/* end */

		User user= new User();
		user.setUsername(userName);
		
		
		
		List<Task> list = null;
		try {
			
			
			Date date = CustomDataSerializer.FORMATTER.parse(taskDate);
			
			list = taskService.getTaskListV1(user,date);

			/* for void response body */
			payload = new Payload<List<Task>>(list);
			responseEntity = new ResponseEntity<Payload<List<Task>>>(payload, HttpStatus.OK);
			logger.info("completed allTasks request");

			return responseEntity;
			/* end */

		} catch (Exception e) {
			logger.error("No task found", e);
			payload = new Payload<List<Task>>(new TaskException("No task found"));
			responseEntity = new ResponseEntity<Payload<List<Task>>>(payload, HttpStatus.NOT_FOUND);
			return responseEntity;
		}

	}
	@GetMapping("/allTasksByUser/{taskDate}/{userName}")
	public ResponseEntity<Payload<List<Task>>> allTasksByUser(@PathVariable("taskDate") String taskDate,@PathVariable("userName") String userName,HttpSession session) {
		logger.info("started allTasksByUser request");
		
		
		/* for response payload */
		Payload<List<Task>> payload = null;
		ResponseEntity<Payload<List<Task>>> responseEntity = null;
		/* end */

		User user= new User();
		user.setUsername(userName);
		
		
		
		List<Task> list = null;
		try {
			
			
			Date date = CustomDataSerializer.FORMATTER.parse(taskDate);
			
			list = taskService.getTaskList(user,date);

			/* for void response body */
			payload = new Payload<List<Task>>(list);
			responseEntity = new ResponseEntity<Payload<List<Task>>>(payload, HttpStatus.OK);
			logger.info("completed allTasks request");

			return responseEntity;
			/* end */

		} catch (Exception e) {
			logger.error("No task found", e);
			payload = new Payload<List<Task>>(new TaskException("No task found"));
			responseEntity = new ResponseEntity<Payload<List<Task>>>(payload, HttpStatus.NOT_FOUND);
			return responseEntity;
		}

	}
	@GetMapping("/getTask/{taskId}")
	public ResponseEntity<Payload<Task>> getTask(@PathVariable("taskId") Long taskId) {
		logger.info("started getTask request");
	
		
		/* for response payload */
		Payload<Task> payload = null;
		ResponseEntity<Payload<Task>> responseEntity = null;
		/* end */

		try {
			Task task = new Task();
			task.setTaskId(taskId);
			task = taskService.getTask(task);

			/* for void response body */
			payload = new Payload<Task>(task);
			responseEntity = new ResponseEntity<Payload<Task>>(payload, HttpStatus.CREATED);
			
			logger.info("completed getTask request");
			return responseEntity;
			/* end */
		} catch (Exception e) {
			payload = new Payload<Task>(new TaskException("Fail to get the task"));
			responseEntity = new ResponseEntity<Payload<Task>>(payload, HttpStatus.NOT_FOUND);
			return responseEntity;
		}

	}


}
