package com.github.sergiolopes.niceurls.parser;

import com.github.sergiolopes.niceurls.results.DoNothing;
import com.github.sergiolopes.niceurls.results.MovedPermanently;
import com.github.sergiolopes.niceurls.results.MovedTemporarily;
import com.github.sergiolopes.niceurls.results.ResultStrategy;
import com.github.sergiolopes.niceurls.results.ServerSideRedirect;

/**
 * Represents a Route defined by the user. Essentially we have many types of
 * routes, with different syntaxes and different Consequences;
 */
public enum RouteType {
	
	// order matters!! be careful
	IGNORE            (">>!", new DoNothing()),
	MOVED_PERMANENTLY (">>>", new MovedPermanently()), 
	REDIRECT          (">>" , new MovedTemporarily()), 
	SKIP_TO_VIEW      ("=>" , new ServerSideRedirect());

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
