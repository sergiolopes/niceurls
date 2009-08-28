package com.github.sergiolopes.niceurls.results;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;


public class MovedTemporarilyTest extends BaseResultStrategyTest {

	private MovedTemporarily redirect;

	@Before
	public void setup() {
		this.redirect = new MovedTemporarily();
	}
	
	@Test
	public void shouldCallSendRedirect() throws IOException {
		final String uri = "/anything";
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(""));
			one(response).sendRedirect(uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	@Test
	public void localRedirectWithAbsolutePath() throws IOException {
		final String uri = "/anything";
		final String contextPath = "/context";
		
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(contextPath));
			one(response).sendRedirect(contextPath + uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	@Test
	public void localRedirectWithRelativePath() throws IOException {
		final String uri = "anything";
		final String contextPath = "/context";
		
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(contextPath));
			one(response).sendRedirect(uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	
	@Test
	public void externalRedirect() throws IOException {
		final String uri = "http://anything";
		final String contextPath = "/context";
		
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(contextPath));
			one(response).sendRedirect(uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
}
