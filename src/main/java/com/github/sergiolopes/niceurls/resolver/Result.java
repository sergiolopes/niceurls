package com.github.sergiolopes.niceurls.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.results.ResultStrategy;

public class Result {
	
	private ResultStrategy strategy;
	private String uri;
	private ParamsContext paramsContext;
	

	public Result(ParamsContext paramsContext, ResultStrategy strategy, String uri) {
		this.paramsContext = paramsContext;
		this.strategy = strategy;
		this.uri = uri;
	}

	public ParamsContext getParamsContext() {
		return paramsContext;
	}
	
	/**
	 * Executes this result with the given request and response
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		strategy.execute(uri, request, response);		
	}
}
