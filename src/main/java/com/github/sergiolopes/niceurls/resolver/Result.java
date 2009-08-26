package com.github.sergiolopes.niceurls.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sergiolopes.niceurls.results.ResultStrategy;

public class Result {
	
	private ResultStrategy strategy;
	private String uri;
	private ParamsContext paramsContext;
	

	void setStrategy(ResultStrategy strategy) {
		this.strategy = strategy;
	}

	void setUri(String uri) {
		this.uri = uri;
	}
	public ParamsContext getParamsContext() {
		return paramsContext;
	}
	void setParamsContext(ParamsContext paramsContext) {
		this.paramsContext = paramsContext;
	}
	
	/**
	 * Executes this result with the given request and response
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		strategy.execute(uri, request, response);		
	}
}
