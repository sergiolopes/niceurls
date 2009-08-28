package com.github.sergiolopes.niceurls.resolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import com.github.sergiolopes.niceurls.http.ParamsContext;
import com.github.sergiolopes.niceurls.parser.RouteType;

public class URLResolverTest {

	protected Mockery context = new Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private Result nullResult = context.mock(Result.class, "nullResult");
	private URLResolver resolver = new URLResolver(nullResult);
	
	@Test
	public void testNoRouteAdded() {
		assertThat(resolver.getRoutes().size(), is(0));
		assertThat(resolver.resolveURL("/anything"), is(nullResult));
	}
	
	@Test
	public void testOneRouteAdded() {
		String expectedTo1 = addSimpleRoute("/anything");
		Result result = resolver.resolveURL("/anything");
		
		assertThat(resolver.getRoutes().size(), is(1));
		assertThat(result.getUri(), is(expectedTo1));
		assertThat(result.getStrategy(), is(RouteType.REDIRECT.getResultStrategy()));
		assertThat(resolver.resolveURL("/other"), is(equalTo(nullResult)));
	}
	
	@Test
	public void testTwoRouteAdded() {
		String expectedTo1 = addSimpleRoute("/anything");
		String expectedTo2 = addSimpleRoute("/anything/path/");
		
		assertThat(resolver.getRoutes().size(), is(2));
		assertThat(resolver.resolveURL("/anything").getUri(), is(expectedTo1));
		assertThat(resolver.resolveURL("/anything/path/").getUri(), is(expectedTo2));
		
		// not found
		assertThat(resolver.resolveURL("/other"), is(equalTo(nullResult)));
		assertThat(resolver.resolveURL("/anything/"), is(equalTo(nullResult)));
		assertThat(resolver.resolveURL("/anything/path"), is(equalTo(nullResult)));
	}
	
	@Test
	public void testThreeRouteAddedWithParametersAndWildcards() {
		String expectedTo1 = addSimpleRoute("/anything/([^/]*)/"); // param
		String expectedTo2 = addSimpleRoute("/anything/.*/path/"); // wildcard
		String expectedTo3 = addSimpleRoute("/anything/([^/]*)/path/.*"); // param / wildcard
		
		assertThat(resolver.getRoutes().size(), is(3));
		assertThat(resolver.resolveURL("/anything/wow/").getUri(), is(expectedTo1));
		assertThat(resolver.resolveURL("/anything/path/").getUri(), is(expectedTo1));
		assertThat(resolver.resolveURL("/anything/waw/path/").getUri(), is(expectedTo2));
		assertThat(resolver.resolveURL("/anything/w/path/").getUri(), is(expectedTo2));
		assertThat(resolver.resolveURL("/anything/w/path/z").getUri(), is(expectedTo3));
		assertThat(resolver.resolveURL("/anything//path/x").getUri(), is(expectedTo3));
		
		// not found
		assertThat(resolver.resolveURL("/other"), is(equalTo(nullResult)));
		assertThat(resolver.resolveURL("/anything/"), is(equalTo(nullResult)));
		assertThat(resolver.resolveURL("/anything"), is(equalTo(nullResult)));
		assertThat(resolver.resolveURL("/anything/path"), is(equalTo(nullResult)));
	}
	
	@Test
	public void testParamValuesAreCorrect() {
		addSimpleRoute("/anything/([^/]*)/", "param1"); // param
		addSimpleRoute("/anything/([^/]*)/([^/]*)/", "param1", "param2"); // param / param
		addSimpleRoute("/anything/.*/path/([^/]*)", "param1"); // wildcard / param
		
		assertThat(resolver.getRoutes().size(), is(3));
		assertThat(resolver.resolveURL("/anything/wow/").getParamsContext().getParameter("param1"), is("wow"));
		assertThat(resolver.resolveURL("/anything/wow/waw/").getParamsContext().getParameter("param1"), is("wow"));
		assertThat(resolver.resolveURL("/anything/wow/waw/").getParamsContext().getParameter("param2"), is("waw"));
		assertThat(resolver.resolveURL("/anything/wow/path/waw").getParamsContext().getParameter("param1"), is("waw"));
	}
	
	@Test
	public void testThatOrderMatters() {
		String expectedTo1 = addSimpleRoute("/sub/");
		String expectedTo2 = addSimpleRoute("/sub/static1/");
		String expectedTo3 = addSimpleRoute("/sub/static2/");
		String expectedTo4 = addSimpleRoute("/sub/.*"); // wildcard
		
		assertThat(resolver.getRoutes().size(), is(4));
		assertThat(resolver.resolveURL("/sub/").getUri(), is(expectedTo1));
		assertThat(resolver.resolveURL("/sub/static1/").getUri(), is(expectedTo2));
		assertThat(resolver.resolveURL("/sub/static2/").getUri(), is(expectedTo3));
		assertThat(resolver.resolveURL("/sub/static3/").getUri(), is(expectedTo4));
	}
	
	/* route factories */
	
	private String addSimpleRoute(String from, final String... paramNames) {
		int id = resolver.getRoutes().size() + 1;

		final Route route = context.mock(Route.class, "route" + id);
		final Pattern fromPattern = Pattern.compile(from);
		final String expectedTo = "url" + id;
		
		context.checking(new Expectations() {{
			allowing(route).getFromPattern();will(returnValue(fromPattern));
			allowing(route).getType();will(returnValue(RouteType.REDIRECT));
			allowing(route).evaluateTo(with(any(ParamsContext.class)));will(returnValue(expectedTo));
			allowing(route).getParamNames();will(returnValue(Arrays.asList(paramNames)));
		}});
		resolver.addRoute(route);
		
		return expectedTo;
	}
	
}
