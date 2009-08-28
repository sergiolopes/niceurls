package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;


public class ServerSideRedirect implements ResultStrategy {

	private final static Logger logger = Logger.getLogger(ServerSideRedirect.class);
	
	public ExecutionConsequence execute(String uri, HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) logger.debug("Redirecting to " + uri);
		try {
			request.getRequestDispatcher(uri).forward(request, response);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		
		return ExecutionConsequence.REDIRECTED;
	}

}
