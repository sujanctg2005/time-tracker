package com.bh.timetracker.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.timetracker.entity.User;
import com.bh.timetracker.exception.TaskException;
import com.bh.timetracker.service.UserServiceImpl;
import com.bh.timetracker.utility.Payload;
import com.bh.timetracker.utility.Void;

@RestController
@RequestMapping("auth")
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserServiceImpl userService;

	@GetMapping("/login/{userName}/{password}")
	public ResponseEntity<Payload<Void>> login(@PathVariable("userName") String userName,
			@PathVariable("password") String password, HttpSession session) {
		logger.info("login request");

		/* for response payload */
		Payload<Void> payload = null;
		ResponseEntity<Payload<Void>> responseEntity = null;
		/* end */
		try {

			User u = new User();
			u.setUsername(userName);
			u.setPassword(password);
			User user = userService.getUser(u);

			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			String[] roles = { "user" };
			List<String> permissions = Arrays.asList(roles);
			for (String permission : permissions) {
				grantedAuths.add(new SimpleGrantedAuthority(permission));
			}

			Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, grantedAuths);

			logger.info("Logging in  success : ", userName);

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			user.setPassword("");
			session.setAttribute("loginUser", user);

			/* for void response body */
			payload = new Payload<Void>(new Void());
			responseEntity = new ResponseEntity<Payload<Void>>(payload, HttpStatus.OK);
			logger.info("completed createTask request");

			return responseEntity;
			/* end */

		} catch (Exception e) {
			payload = new Payload<Void>(new TaskException("fail to login "));
			responseEntity = new ResponseEntity<Payload<Void>>(payload, HttpStatus.BAD_REQUEST);
			return responseEntity;
		}

	}
}
