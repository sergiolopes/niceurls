package com.github.sergiolopes.niceurls.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.results.ResultStrategy;

public class Result {
	
	private ResultStrategy strategy;
	private String uri;
	private ParamsContext paramsContext;
	
	public Result(ResultStrategy strategy) {
		this.strategy = strategy;
	}
	
	public Result(ParamsContext paramsContext, ResultStrategy strategy, String uri) {
		this(strategy);
		this.paramsContext = paramsContext;
		this.uri = uri;
	}

	public ParamsContext getParamsContext() {
		return paramsContext;
	}
	
	String getUri() {
		return uri;
	}
	
	ResultStrategy getStrategy() {
		return strategy;
	}
	
	/**
	 * Executes this result with the given request and response.
	 * 
	 * @return What happened inside this execution
	 */
	public ExecutionConsequence execute(HttpServletRequest request, HttpServletResponse response) {
		return strategy.execute(uri, request, response);		
	}
}
