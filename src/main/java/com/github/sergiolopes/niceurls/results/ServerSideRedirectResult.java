package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Route;

public class ServerSideRedirectResult extends Result {

	private final static Logger logger = Logger.getLogger(ServerSideRedirectResult.class);
	private String uri;
	
	@Override
	public void init(Route route, ParamsContext paramsContext) {
		uri = route.evaluateTo(paramsContext);
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) logger.debug("Redirecting to " + uri);
		try {
			request.getRequestDispatcher(uri).forward(request, response);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}


	
}
