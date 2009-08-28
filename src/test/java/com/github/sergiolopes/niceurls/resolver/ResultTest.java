package com.github.sergiolopes.niceurls.resolver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.results.ResultStrategy;

public class ResultTest {

	protected Mockery context = new Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private HttpServletRequest request = context.mock(HttpServletRequest.class);
	private HttpServletResponse response = context.mock(HttpServletResponse.class);
	private ResultStrategy resultStrategy = context.mock(ResultStrategy.class);
	private ParamsContext paramsContext = context.mock(ParamsContext.class);
	
	@Test
	public void testExecuteCall() {
		final String uri = "/test";
		
		context.checking(new Expectations(){{
			one(resultStrategy).execute(uri, request, response);
			will(returnValue(ExecutionConsequence.REDIRECTED));
		}});
		
		final Result result = new Result(paramsContext, resultStrategy, uri);
		ExecutionConsequence consequence = result.execute(request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	@Test
	public void testExecuteCallWithDifferentReturnValue() {
		final String uri = "/test";
		
		context.checking(new Expectations(){{
			one(resultStrategy).execute(uri, request, response);
			will(returnValue(ExecutionConsequence.REQUEST_FLOW_NOT_AFFECTED));
		}});
		
		final Result result = new Result(paramsContext, resultStrategy, uri);
		ExecutionConsequence consequence = result.execute(request, response);
		assertThat(consequence, is(ExecutionConsequence.REQUEST_FLOW_NOT_AFFECTED));
	}

}
