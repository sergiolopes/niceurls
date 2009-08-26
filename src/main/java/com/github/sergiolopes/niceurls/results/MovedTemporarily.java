package com.github.sergiolopes.niceurls.results;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class MovedTemporarily extends ResultStrategy {

	private final static Logger logger = Logger.getLogger(MovedTemporarily.class);
	
	@Override
	public void execute(String uri, HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) logger.debug("Redirecting to " + uri);
		try {
			response.sendRedirect(absoluteURL(request, uri));
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}


	
}
