package com.github.sergiolopes.niceurls.parser;

import com.github.sergiolopes.niceurls.results.MovedPermanentlyResult;
import com.github.sergiolopes.niceurls.results.MovedTemporarilyResult;
import com.github.sergiolopes.niceurls.results.ResultStrategy;
import com.github.sergiolopes.niceurls.results.ServerSideRedirectResult;

/**
 * Represents a Route defined by the user. Essentially we have many types of
 * routes, with different syntaxes and different Consequences;
 */
public enum RouteType {
	
	// order matters!! be careful
	IGNORE(">>!", null),
	MOVED_PERMANENTLY (">>>",new MovedPermanentlyResult()), 
	REDIRECT (">>",new MovedTemporarilyResult()), 
	SKIP_TO_VIEW ("=>", new ServerSideRedirectResult());

	private final String separator;
	private final ResultStrategy strategy;
	
	RouteType(String s, ResultStrategy resultStrategy) {
		this.separator = s;
		strategy = resultStrategy;
	}
	
	public String getSeparator() {
		return separator;
	}
	
	public ResultStrategy getResultStrategy() {
		return strategy;
	}
}
