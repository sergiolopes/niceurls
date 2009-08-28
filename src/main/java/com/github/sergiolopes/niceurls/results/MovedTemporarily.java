package com.github.sergiolopes.niceurls.results;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;

public class MovedTemporarily implements ResultStrategy {

	private final static Logger logger = Logger.getLogger(MovedTemporarily.class);
	
	public ExecutionConsequence execute(String uri, HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) logger.debug("Redirecting to " + uri);
		try {
			response.sendRedirect(absoluteURL(request, uri));
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		
		return ExecutionConsequence.REDIRECTED;
	}

	private String absoluteURL(HttpServletRequest request, String uri) {
		if (uri.startsWith("/"))
			return request.getContextPath() + uri;
		else
			return uri;
	}
}
