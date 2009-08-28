package com.github.sergiolopes.niceurls.results;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.sergiolopes.niceurls.resolver.ExecutionConsequence;


public class DoNothingTest extends BaseResultStrategyTest {

	@Test
	public void executeShouldDoNothing() {
		DoNothing doNothing = new DoNothing();
		ExecutionConsequence consequence = doNothing.execute("/anything", request, response);
		assertThat(consequence, is(ExecutionConsequence.REQUEST_FLOW_NOT_AFFECTED));
	}
}
