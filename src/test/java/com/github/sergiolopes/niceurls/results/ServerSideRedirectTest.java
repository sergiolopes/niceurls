package com.github.sergiolopes.niceurls.results;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;



public class ServerSideRedirectTest extends BaseResultStrategyTest {

	private ServerSideRedirect serverSideRedirect;

	@Before
	public void setup() {
		serverSideRedirect = new ServerSideRedirect();
	}
	
	@Test
	public void shouldCallRequestDispatcherAndForward() throws ServletException, IOException {
		final String uri = "/some.jsp";
		final RequestDispatcher dispatcher = context.mock(RequestDispatcher.class);
		
		context.checking(new Expectations(){{
			one(request).getRequestDispatcher(uri); will(returnValue(dispatcher));
			one(dispatcher).forward(request, response);
		}});
		
		ExecutionConsequence consequence = serverSideRedirect.execute(uri, request, response);
		assertThat(consequence, is(ExecutionConsequence.REDIRECTED));
	}
}
