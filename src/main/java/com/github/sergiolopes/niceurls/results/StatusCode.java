package com.github.sergiolopes.niceurls.results;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;

public class StatusCode implements ResultStrategy {

	private final static Logger logger = Logger.getLogger(StatusCode.class);
	private final int statusCode;
	
	public StatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	 
	public ExecutionConsequence execute(String uri, HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) 
			logger.debug("Status code " + statusCode);
		
		try {
			response.sendError(statusCode);
			return ExecutionConsequence.REDIRECTED;
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
}
