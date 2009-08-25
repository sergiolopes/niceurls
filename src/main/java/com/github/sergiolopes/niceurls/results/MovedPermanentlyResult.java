package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Route;

public class MovedPermanentlyResult extends Result {

	private final static Logger logger = Logger.getLogger(MovedPermanentlyResult.class);
	private String uri;
	
	
	@Override
	public void init(Route route, ParamsContext paramsContext) {
		uri = route.evaluateTo(paramsContext);
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) logger.debug("Redirecting to " + absoluteURL(request, uri));
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.addHeader("Location", absoluteURL(request, uri));
		response.addIntHeader("Content-length", 0);
		response.addDateHeader("Date", System.currentTimeMillis());		
	}
}
