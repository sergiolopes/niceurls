package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;

public class MovedPermanently implements ResultStrategy {

	private final static Logger logger = Logger.getLogger(MovedPermanently.class);
	
	public ExecutionConsequence execute(String uri, HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) 
			logger.debug("Redirecting to " + absoluteURL(request, uri));
		
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.addHeader("Location", absoluteURL(request, uri));
		response.addIntHeader("Content-length", 0);
		response.addDateHeader("Date", System.currentTimeMillis());
		
		return ExecutionConsequence.REDIRECTED;
	}
	
	private String absoluteURL(HttpServletRequest request, String uri) {
		if (uri.startsWith("/"))
			uri = request.getContextPath() + uri;
		
		if (request.getQueryString() != null)
			uri = uri + "?" + request.getQueryString();
		
		return uri;
	}
}
