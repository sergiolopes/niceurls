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
	IGNORE(">>!") {
		public ResultStrategy createResultStrategy() {
			return null;
		}
	},
	MOVED_PERMANENTLY (">>>") {
		public ResultStrategy createResultStrategy() {
			return new MovedPermanentlyResult();
		}
	}, 
	REDIRECT (">>") {
		public ResultStrategy createResultStrategy() {
			return new MovedTemporarilyResult();
		}
	}, 
	SKIP_TO_VIEW ("=>") {
		public ResultStrategy createResultStrategy() {
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
	
	public abstract ResultStrategy createResultStrategy();
}
