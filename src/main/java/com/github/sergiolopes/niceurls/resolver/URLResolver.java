package com.github.sergiolopes.niceurls.resolver;



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
