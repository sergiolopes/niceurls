package com.github.sergiolopes.niceurls.consequences;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Result;
import com.github.sergiolopes.niceurls.resolver.Route;

public class IgnoreRouteConsequence implements RouteConsequence {

	public Result generateResult(Route route, ParamsContext context) {
		return null;
	}

}
