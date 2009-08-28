package com.github.sergiolopes.niceurls.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.parser.RouteType;
import com.github.sergiolopes.niceurls.results.ResultStrategy;


/**
 * Default implementation to a URLResolver
 */
public class URLResolver {

	private final static Logger logger = Logger.getLogger(URLResolver.class);
	private final List<Route> routes;
	private Result nullResult = new Result(RouteType.IGNORE.getResultStrategy());
	
	public URLResolver() {
		this.routes = new ArrayList<Route>();
	}
	
	URLResolver(Result nullResult) { /* for tests only */
		this();
		this.nullResult = nullResult;
	}
	
	public void addRoute(Route route) {
		this.routes.add(route);
	}

	/**
	 * Resolve some given URL
	 * @param url URL relative to context
	 * @return A {@link Result} that should not be null
	 */
	public Result resolveURL(String url) {
		if (logger.isTraceEnabled()) logger.trace("Trying to resolve " + url);
		
		for (Route route : this.routes) {
			Matcher m = route.getFromPattern().matcher(url);
			if (m.matches()) {
				
				ParamsContext paramsContext = new ParamsContext();
				// parse variables
				int i = 1;
				for (String paramName : route.getParamNames()) {
					paramsContext.addParameter(paramName, m.group(i++));
				}

				String uri = route.evaluateTo(paramsContext);
				ResultStrategy resultStrategy = route.getType().getResultStrategy();
				return new Result(paramsContext, resultStrategy, uri);
			}
		}
		
		if (logger.isTraceEnabled()) logger.trace("NiceURL doesn't know this URL");
		return nullResult;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
}
