package com.bh.timetracker.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AjaxAwareAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {
	public AjaxAwareAuthenticationEntryPoint(String loginUrl) {
		super(loginUrl);
	}

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String ajaxHeader = request.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
		if (isAjax) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.addHeader("LOGIN-URL", SecurityConfig.LOGIN_URL);
			
		} else {
			super.commence(request, response, authException);
		}
	}
}