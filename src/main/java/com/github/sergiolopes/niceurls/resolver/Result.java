package com.github.sergiolopes.niceurls.resolver;

import com.github.sergiolopes.niceurls.results.ResultStrategy;

public class Result {
	
	private ResultStrategy strategy;
	private String uri;
	private ParamsContext paramsContext;
	
	public ResultStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(ResultStrategy strategy) {
		this.strategy = strategy;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public ParamsContext getParamsContext() {
		return paramsContext;
	}
	public void setParamsContext(ParamsContext paramsContext) {
		this.paramsContext = paramsContext;
	}
}
