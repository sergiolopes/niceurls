package com.github.sergiolopes.niceurls.results;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;


public class StatusCodeTest extends BaseResultStrategyTest {

	private StatusCode status404;
	private StatusCode status410;

	@Before
	public void setup() {
		this.status404 = new StatusCode(404);
		this.status410 = new StatusCode(410);
	}
	
	private void headerExpectations(final int status) {
		context.checking(new Expectations(){{
			try {
				one(response).sendError(status);
				allowing(request).getQueryString();will(returnValue(null));
			} catch (Exception e) {}
		}});
	}
	
	@Test
	public void test404() throws IOException {
		headerExpectations(404);
		
		final String uri = "/notFound";
		ExecutionConsequence consequence = status404.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	@Test
	public void test410() throws IOException {
		headerExpectations(410);
		
		final String uri = "/gone";
		ExecutionConsequence consequence = status410.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
}
