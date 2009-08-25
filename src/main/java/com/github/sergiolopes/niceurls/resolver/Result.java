package com.github.sergiolopes.niceurls.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents a result to an URL that should be executed.
 * It has a componentName, a logicName and one parameters Map. 
 */
public class Result {

	private ParamsContext paramsContext;

	public void setParamsContext(ParamsContext paramsContext) {
		this.paramsContext = paramsContext;
	}
	
	public Map<String, String> getParameters() {
		return paramsContext.getParameters();
	}
	
	@Deprecated
	public void setComponentName(String componentName) {
	}
	
	@Deprecated
	public void setLogicName(String logicName) {
	}
	
	public void execute (HttpServletRequest request, HttpServletResponse response) {
		// TODO execute this result, whatever this means
	}
	
}
