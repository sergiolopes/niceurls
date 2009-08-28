package com.github.sergiolopes.niceurls.resolver;

/**
 * Indicates what happened inside a {@link Result} execution.
 * 
 * @author Sérgio Lopes
 */
public enum ExecutionConsequence {

	/**
	 * The {@link Result} caused a redirection to somewhere
	 */
	REDIRECTED, 
	
	/**
	 * The request flow is not affected by this execution
	 * and probably should continue normally
	 */
	REQUEST_FLOW_NOT_AFFECTED;
	
}
