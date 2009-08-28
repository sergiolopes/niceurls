package com.github.sergiolopes.niceurls.resolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.parser.RouteType;

/**
 * Test only 'to' methods in {@link Route}
 * @author sergio
 */
public class RouteToTest {
	protected Mockery context = new Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private Route route;
	private ParamsContext paramsContext;
	
	@Test
	public void testSimpleToURLEvaluation() {
		evaluateTo("/path", "/path");
	}

	@Test
	public void testToURLEvaluationWithOneVariable() {
		expectParamValue("param", "something");
		evaluateTo("/path/#{param}/", "/path/something/");
	}
	
	@Test
	public void testToURLEvaluationWithTwoVariables() {
		expectParamValue("param", "something");
		expectParamValue("otherParam", "otherSomething");
		evaluateTo("/#{param}/test/#{otherParam}.jsp", "/something/test/otherSomething.jsp");
	}
	
	/* helper methods */
	private void expectParamValue(final String name, final String value) {
		context.checking(new Expectations() {{
			allowing(paramsContext).getParameter(name);will(returnValue(value));
		}});
	}
	
	private void evaluateTo(String to, String expectedResult) {
		this.route = new Route("anything", to, RouteType.REDIRECT);
		String evaluatedTo = route.evaluateTo(paramsContext);
		assertThat(evaluatedTo, is(equalTo(expectedResult)));
	}

	@Before
	public void configureParamsContext() {
		this.paramsContext = context.mock(ParamsContext.class);
	}
	
}
