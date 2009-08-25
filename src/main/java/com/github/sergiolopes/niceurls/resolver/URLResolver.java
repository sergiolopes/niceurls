package com.github.sergiolopes.niceurls.resolver;

import com.github.sergiolopes.niceurls.results.Result;


/**
 * What does an URLResolver do? Resolves URLs.
 */
public interface URLResolver {
	/**
	 * Given an url, returns the Result
	 * @param url
	 * @return the Result
	 */
	Result resolveURL (String url);
	
	/**
	 * 
	 * @param route
	 * @param result
	 * @param rc
	 */
	void addRoute(Route route);
}
