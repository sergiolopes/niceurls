package com.github.sergiolopes.niceurls.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.results.Result;

/**
 * Default implementation to a URLResolver
 */
public class DefaultURLResolver implements URLResolver {

	private final static Logger logger = Logger.getLogger(DefaultURLResolver.class);
	private final List<Route> routes;
	
	public DefaultURLResolver() {
		this.routes = new ArrayList<Route>();
	}
	
	public void addRoute(Route route) {
		this.routes.add(route);
	}

	public Result resolveURL(String url) {
		if (logger.isTraceEnabled()) logger.trace("Trying to resolve " + url);
		
		for (Route route : this.routes) {
			Matcher m = route.getFromPattern().matcher(url);
			if (m.matches()) {
				
				ParamsContext context = new ParamsContext();
				// parse variables
				int i = 1;
				for (String param : route.getParamNames()) {
					context.addParameter(param, m.group(i++));
				}

				Result result = route.generateResult(context);
				if (result != null)
					result.setParamsContext(context);
				if (logger.isTraceEnabled()) logger.trace("Result found: " + result);
				return result;
			}
		}
		if (logger.isTraceEnabled()) logger.trace("NiceURL doesn't know this URL");
		return null;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}


}
