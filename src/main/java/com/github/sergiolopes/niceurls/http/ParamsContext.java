package com.github.sergiolopes.niceurls.http;

import java.util.HashMap;
import java.util.Map;

public class ParamsContext {

	private Map<String, String> parameters = new HashMap<String, String>();
	
	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}
	
	public String getParameter(String key) {
		return this.parameters.get(key);
	}
	
	public Map<String, String> getParametersAsMap() {
		return parameters;
	}

}
