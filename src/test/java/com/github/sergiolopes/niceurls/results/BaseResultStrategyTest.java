package com.github.sergiolopes.niceurls.results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;

public class BaseResultStrategyTest {

	protected Mockery context = new Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@Before
	public void mockEverything() {
		request = context.mock(HttpServletRequest.class);
		response = context.mock(HttpServletResponse.class);
	}
	
}
