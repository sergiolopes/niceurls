package com.github.sergiolopes.niceurls.resolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.parser.RouteType;


/**
 * Represents a NiceURL compiled Route.
 * 
 * It has a regexp that the final URL should match.
 * Also has a list of parameters names.
 */
public class Route {
	
	private final String to;
	private final RouteType type;
	private final List<String> paramNames = new LinkedList<String>();
	
	private Pattern fromPattern;

	public Route(String from, String to, RouteType type) {
		this.type = type;
		this.parseFrom(from);
		this.to = to;
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
	
	public RouteType getType() {
		return type;
	}
	
	public Pattern getFromPattern() {
		return fromPattern;
	}
	
	public List<String> getParamNames() {
		return Collections.unmodifiableList(paramNames);
	}
	
	public String evaluateTo(ParamsContext context) {
		if (this.to == null) {
			return null;
		}
		
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
