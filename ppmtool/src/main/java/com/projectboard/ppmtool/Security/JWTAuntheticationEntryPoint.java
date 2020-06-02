package com.projectboard.ppmtool.Security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.projectboard.ppmtool.exceptions.InvalidLoginResponse;

@Component
public class JWTAuntheticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
		String jsonLoginResponse = new Gson().toJson(invalidLoginResponse);
		
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setStatus(401);
		httpServletResponse.getWriter().print(jsonLoginResponse);
		
	}

	
	
}
