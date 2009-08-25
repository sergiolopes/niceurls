package com.github.sergiolopes.niceurls.parser;

import com.github.sergiolopes.niceurls.consequences.IgnoreRouteConsequence;
import com.github.sergiolopes.niceurls.consequences.MovedPermanentlyRouteConsequence;
import com.github.sergiolopes.niceurls.consequences.RedirectRouteConsequence;
import com.github.sergiolopes.niceurls.consequences.RouteConsequence;
import com.github.sergiolopes.niceurls.consequences.SkipToViewRouteConsequence;
import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Route;
import com.github.sergiolopes.niceurls.results.Result;

/**
 * Represents a Route defined by the user. Essentially we have many types of
 * routes, with different syntaxes and different Consequences;
 */
public enum RouteType {
	
	// order matters!! be careful
	IGNORE(">>!", new IgnoreRouteConsequence()),
	MOVED_PERMANENTLY (">>>", new MovedPermanentlyRouteConsequence()), 
	REDIRECT (">>", new RedirectRouteConsequence()), 
	SKIP_TO_VIEW ("=>", new SkipToViewRouteConsequence());

	private final String separator;
	private final RouteConsequence routeConsequence;
	
	RouteType(String s, RouteConsequence rc) {
		this.separator = s;
		this.routeConsequence = rc;
	}
	
	public String getSeparator() {
		return separator;
	}
	
	public RouteConsequence getRouteConsequence() {
		return routeConsequence;
	}

	public Result generateResult(Route route, ParamsContext context) {
		return routeConsequence.generateResult(route, context);
	}
}
