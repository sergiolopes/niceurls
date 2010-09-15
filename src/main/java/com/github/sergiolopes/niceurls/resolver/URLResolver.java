package com.github.sergiolopes.niceurls.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
	
	// TODO change this to some LFU impl
	private final Map<String, Result> urlToResultCache = new ConcurrentHashMap<String, Result>();
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
		
		Result result = urlToResultCache.get(url);
		if (result == null) {
			for (Route route : this.routes) {
				Matcher matcher = route.getFromPattern().matcher(url);
				if (matcher.matches()) {
					ParamsContext paramsContext = new ParamsContext();
					
					// parse variables
					int i = 1;
					for (String paramName : route.getParamNames()) {
						paramsContext.addParameter(paramName, matcher.group(i++));
					}

					String uri = route.evaluateTo(paramsContext);
					ResultStrategy resultStrategy = route.getType().getResultStrategy();
					result = new Result(paramsContext, resultStrategy, uri);
					
					urlToResultCache.put(url, result);
					break;
				}
			}
			
		} else {
			if (logger.isTraceEnabled()) 
				logger.trace("Found in cache ");
		}

		if (result != null) return result;
		
		if (logger.isTraceEnabled()) logger.trace("NiceURL doesn't know this URL");
		return nullResult;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
}
