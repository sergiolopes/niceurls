package com.github.sergiolopes.niceurls.resolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import com.github.sergiolopes.niceurls.parser.RouteType;

/**
 * Test only 'from' methods in {@link Route} 
 * @author sergio
 */
public class RouteFromTest {
	protected Mockery context = new Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private Route route;
	
	/** SECTION: Test From: **/
	
	@Test
	public void testSimpleFromURL() {
		final String from = "/simple";
		testFrom(from, from);
	}
	
	@Test
	public void testSimpleFromURLWithPath() {
		final String from = "/simple/path";
		testFrom(from, from);
	}
	
	@Test
	public void testFromURLWithWildcard() {
		final String from = "/path/*/";
		final String expectedPattern = "/path/.*/";
		testFrom(from, expectedPattern);
	}
	
	@Test
	public void testFromURLWithOneVariable() {
		final String from = "/path/:{variable}/";
		final String expectedPattern = "/path/([^/]*)/";
		testFrom(from, expectedPattern, "variable");
	}
	
	@Test
	public void testFromURLWithOneVariableAndOneWildcard() {
		final String from = "/path/*/:{variable}/";
		final String expectedPattern = "/path/.*/([^/]*)/";
		testFrom(from, expectedPattern, "variable");
	}
	
	@Test
	public void testFromURLWithTwoVariable() {
		final String from = "/path/:{variable}/something/:{otherVariable}";
		final String expectedPattern = "/path/([^/]*)/something/([^/]*)";
		testFrom(from, expectedPattern, "variable", "otherVariable");
	}
	
	@Test
	public void testFromURLWithOneVariableUsingDots() {
		final String from = "/path/:{bean.field}";
		final String expectedPattern = "/path/([^/]*)";
		testFrom(from, expectedPattern, "bean.field");
	}
	
	@Test(expected=PatternSyntaxException.class)
	public void testFromURLWithWrongVariable() {
		final String from = "/path/:{variable-invalid}/";
		testFrom(from, "nothing");
	}
	
	@Test(expected=PatternSyntaxException.class)
	public void testOtherFromURLWithWrongVariable() {
		final String from = "/path/:{variable_invalid}/";
		testFrom(from, "nothing");
	}

	/* helper method to test fromPattern and extracted parameter names */
	private void testFrom(final String from, final String expectedPattern, String... expectedParameters) {
		this.route = new Route(from, "/anything", RouteType.REDIRECT);
		assertThat(route.getFromPattern().toString(), is(equalTo(expectedPattern)));
		
		List<String> paramNames = route.getParamNames();
		for (String param : expectedParameters) {
			assertThat(paramNames.contains(param), is(true));
		}
		
		if (expectedParameters.length == 0)
			assertThat(paramNames.size(), is(0));
	}
}
