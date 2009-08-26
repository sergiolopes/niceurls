package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents a result to an URL that should be executed.
 * It has a componentName, a logicName and one parameters Map.
 * 
 *  Implementations should be prepared to instance sharing (possibly by not having state).
 */
public interface ResultStrategy {
	
	void execute (String uri, HttpServletRequest request, HttpServletResponse response);
}
