package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;

public class DoNothing implements ResultStrategy {

	@Override
	public ExecutionConsequence execute(String uri, HttpServletRequest request, HttpServletResponse response) {
		return ExecutionConsequence.REQUEST_FLOW_NOT_AFFECTED;
	}

}
