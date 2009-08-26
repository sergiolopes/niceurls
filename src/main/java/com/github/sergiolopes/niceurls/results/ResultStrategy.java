package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents a result to an URL that should be executed.
 * It has a componentName, a logicName and one parameters Map. 
 */
public abstract class ResultStrategy {

	/**
	 * Utility method
	 * 
	 * @param request
	 * @param uri
	 * @return
	 */
	protected final String absoluteURL(HttpServletRequest request, String uri) {
		if (uri.startsWith("/"))
			return request.getContextPath() + uri;
		else
			return uri;
	}
	
	public abstract void execute (String uri, HttpServletRequest request, HttpServletResponse response);
}
