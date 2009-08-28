package com.github.sergiolopes.niceurls.results;

import static org.hamcrest.Matchers.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;


public class MovedPermanentlyTest extends BaseResultStrategyTest {

	private MovedPermanently redirect;

	@Before
	public void setup() {
		this.redirect = new MovedPermanently();
	}
	
	private void headerExpectations() {
		context.checking(new Expectations(){{
			one(response).addIntHeader("Content-length", 0);
			one(response).setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			one(response).addDateHeader(with(equalTo("Date")), with(greaterThan(System.currentTimeMillis())));
		}});
	}
	
	@Test
	public void shouldRedirect() throws IOException {
		headerExpectations();
		
		final String uri = "/anything";
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(""));
			one(response).addHeader("Location", uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	@Test
	public void localRedirectWithAbsolutePath() throws IOException {
		headerExpectations();
		
		final String uri = "/anything";
		final String contextPath = "/context";
		
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(contextPath));
			one(response).addHeader("Location", contextPath + uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	@Test
	public void localRedirectWithRelativePath() throws IOException {
		headerExpectations();
		
		final String uri = "anything";
		final String contextPath = "/context";
		
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(contextPath));
			one(response).addHeader("Location", uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
	
	
	@Test
	public void externalRedirect() throws IOException {
		headerExpectations();
		
		final String uri = "http://anything";
		final String contextPath = "/context";
		
		context.checking(new Expectations(){{
			allowing(request).getContextPath();will(returnValue(contextPath));
			one(response).addHeader("Location", uri);
		}});
		
		ExecutionConsequence consequence = redirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
}
