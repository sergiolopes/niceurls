package com.github.sergiolopes.niceurls.results;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class MovedTemporarilyResult extends Result {

	private final static Logger logger = Logger.getLogger(MovedTemporarilyResult.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) logger.debug("Redirecting to " + uri);
		try {
			response.sendRedirect(absoluteURL(request, uri));
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}


	
}
