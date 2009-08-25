package com.github.sergiolopes.niceurls.resolver;

import java.util.Map;

/**
 * Represents a result to an URL that should be executed.
 * It has a componentName, a logicName and one parameters Map. 
 */
public class Result {

	private String logicName;
	private String componentName;
	private ParamsContext paramsContext;

	public String getComponentName() {
		return componentName;
	}
	
	public String getLogicName() {
		return logicName;
	}

	public void setParamsContext(ParamsContext paramsContext) {
		this.paramsContext = paramsContext;
	}
	
	public String getParameter(String key) {
		return paramsContext.getParameter(key);
	}
	
	public Map<String, String> getParameters() {
		return paramsContext.getParameters();
	}
	
	@Override
	public String toString() {
		return String.format("[component: %s, logic: %s]", componentName,logicName);
	}
	
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}
	
}
