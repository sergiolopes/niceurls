package com.github.sergiolopes.niceurls.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;
public class RouteTypeTest {

	@Test
	public void ensureThatMovedPermantlyIsBeforeRedirect() {
		assertThat(RouteType.MOVED_PERMANENTLY.ordinal(), is(lessThan(RouteType.REDIRECT.ordinal())));
	}

	@Test
	public void ensureThatIgnoreIsBeforeRedirect() {
		assertThat(RouteType.IGNORE.ordinal(), is(lessThan(RouteType.REDIRECT.ordinal())));
	}
}
