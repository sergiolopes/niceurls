package com.github.sergiolopes.niceurls.resolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.sergiolopes.niceurls.consequences.RouteConsequence;


/**
 * Represents a NiceURL compiled Route.
 * 
 * It has a regexp that the final URL should match.
 * Also has a result (usually component and logic names) and a list of parameters names.
 */
public class Route {
	
	private Pattern fromPattern;
	private String to;
	private RouteConsequence routeConsequence;
	private List<String> paramNames = new LinkedList<String>();
	
	public Route(String from, String to, RouteConsequence rc) {
		this.parseFrom(from);
		this.to = to;
		this.routeConsequence = rc;
	}
	
	/**
	 * Generate Pattern based on route from
	 * @param from The route begin String
	 */
	private void parseFrom(String from) {
		// add params  (dots allowed)
		Pattern pattern = Pattern.compile("[^:]*:\\{([A-Za-z0-9\\.]+)\\}");
		Matcher matcher = pattern.matcher(from);
		
		while (!matcher.hitEnd()) {
			if(!matcher.find()) break;
			this.paramNames.add(matcher.group(1));
		}
		
		// variables starts with : (dots allowed)
		// matches one path level
		from = from.replaceAll(":\\{[A-Za-z0-9\\.]+\\}", "([^/]THISISAHACK)");

		// escape dots
		from = from.replaceAll("\\.", "\\\\.");

		// wildcard matches anything in the url
		from = from.replaceAll("\\*", "(.*)");
		
		from = from.replaceAll("THISISAHACK", "*");
		
		this.fromPattern = Pattern.compile(from);
		
	}
	
	public Result generateResult(ParamsContext context) {
		return routeConsequence.generateResult(this, context);
	}

	public Pattern getFromPattern() {
		return fromPattern;
	}
	
	public List<String> getParamNames() {
		return Collections.unmodifiableList(paramNames);
	}
	
	public RouteConsequence getRouteConsequence() {
		return routeConsequence;
	}
	
	
	public String evaluateTo(ParamsContext context) {
		// find all variables and substitutes with parameter values
		Pattern pattern = Pattern.compile("[^#]*\\#\\{([A-Za-z0-9\\.]+)\\}");
		Matcher matcher = pattern.matcher(this.to);

		String finalConsequence = this.to;
		while (!matcher.hitEnd()) {
			if(!matcher.find()) {
				break;
			}
			String var = matcher.group(1);
			finalConsequence = finalConsequence.replace("#{" + var + "}", context.getParameter(var));
		}
		
		return finalConsequence;
	}

}