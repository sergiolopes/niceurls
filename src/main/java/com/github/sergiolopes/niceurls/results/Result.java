package com.github.sergiolopes.niceurls.results;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Route;

/**
 * Represents a result to an URL that should be executed.
 * It has a componentName, a logicName and one parameters Map. 
 */
public abstract class Result {

	private ParamsContext paramsContext;

	public void setParamsContext(ParamsContext paramsContext) {
		this.paramsContext = paramsContext;
	}
	
	public Map<String, String> getParameters() {
		return paramsContext.getParameters();
	}
	
	public abstract void init (Route route, ParamsContext paramsContext);
	public abstract void execute (HttpServletRequest request, HttpServletResponse response);

	protected String absoluteURL(HttpServletRequest request, String uri) {
		if (uri.startsWith("/"))
			return request.getContextPath() + uri;
		else
			return uri;
	}
	
}
