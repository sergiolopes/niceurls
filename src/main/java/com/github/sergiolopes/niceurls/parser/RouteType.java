package com.github.sergiolopes.niceurls.parser;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Route;
import com.github.sergiolopes.niceurls.results.MovedPermanentlyResult;
import com.github.sergiolopes.niceurls.results.MovedTemporarilyResult;
import com.github.sergiolopes.niceurls.results.Result;
import com.github.sergiolopes.niceurls.results.ServerSideRedirectResult;

/**
 * Represents a Route defined by the user. Essentially we have many types of
 * routes, with different syntaxes and different Consequences;
 */
public enum RouteType {
	
	// order matters!! be careful
	IGNORE(">>!") {
		public Result createResult() {
			return null;
		}
	},
	MOVED_PERMANENTLY (">>>") {
		public Result createResult() {
			return new MovedPermanentlyResult();
		}
	}, 
	REDIRECT (">>") {
		public Result createResult() {
			return new MovedTemporarilyResult();
		}
	}, 
	SKIP_TO_VIEW ("=>") {
		public Result createResult() {
			return new ServerSideRedirectResult();
		}
	};

	private final String separator;
	
	RouteType(String s) {
		this.separator = s;
	}
	
	public String getSeparator() {
		return separator;
	}
	
	abstract Result createResult();
	
	public Result generateResult(Route route, ParamsContext context) {
		Result result = createResult();
		if (result != null)
			result.init(route, context);
		return result;
	}
}
