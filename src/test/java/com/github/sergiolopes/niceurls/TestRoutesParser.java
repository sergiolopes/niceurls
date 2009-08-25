package com.github.sergiolopes.niceurls;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.parser.RouteDefinition;
import com.github.sergiolopes.niceurls.parser.RoutesParser;
import com.github.sergiolopes.niceurls.resolver.DefaultURLResolver;
import com.github.sergiolopes.niceurls.resolver.Route;


public class TestRoutesParser {

	private DefaultURLResolver resolver;

	@Before
	public void setup() throws IOException {
		resolver = new DefaultURLResolver();
		RoutesParser parser = new RoutesParser(resolver);
		parser.parseFile( this.getClass().getResource("/TestRoutesParser.routes").getFile());
	}

	@Test
	public void testRoutesNumber() {
		Assert.assertEquals(10, resolver.getRoutes().size());
	}
	
	@Test
	public void testConsequences () {
		List<Route> routes = resolver.getRoutes();
//		Assert.assertEquals(RouteDefinition.LOGIC.getRouteConsequence(), routes.get(0).getRouteConsequence());
//		Assert.assertEquals(RouteDefinition.LOGIC.getRouteConsequence(), routes.get(1).getRouteConsequence());
//		Assert.assertEquals(RouteDefinition.LOGIC.getRouteConsequence(), routes.get(2).getRouteConsequence());
//		Assert.assertEquals(RouteDefinition.LOGIC.getRouteConsequence(), routes.get(3).getRouteConsequence());
		Assert.assertEquals(RouteDefinition.REDIRECT.getRouteConsequence(), routes.get(4).getRouteConsequence());
		Assert.assertEquals(RouteDefinition.REDIRECT.getRouteConsequence(), routes.get(5).getRouteConsequence());
		Assert.assertEquals(RouteDefinition.MOVED_PERMANENTLY.getRouteConsequence(), routes.get(7).getRouteConsequence());
		Assert.assertEquals(RouteDefinition.SKIP_TO_VIEW.getRouteConsequence(), routes.get(8).getRouteConsequence());
//		Assert.assertEquals(RouteDefinition.LOGIC.getRouteConsequence(), routes.get(9).getRouteConsequence());
	}
	
	@Test
	public void testFroms() {
		String[] froms = {
				"/",
				"/about/",
				"/images/*/",
				"/blog/:year/:month/:day/",
				"/contact.jsp",
				"/test/",
				"/vraptor/",
				"/old.jsp",
				"/mycompany/",
				"/:component.:logic.logic"
		};
		List<Route> routes = resolver.getRoutes();
		for (int i = 0; i < froms.length; i++) {
			Pattern p = getPatternForRouteFrom(froms[i]);
			Assert.assertEquals(p.pattern(), routes.get(i).getFromPattern().pattern());
		}
	}
	
	private Pattern getPatternForRouteFrom(String from) {
		return new Route(from, null,null).getFromPattern();
	}
	
}
