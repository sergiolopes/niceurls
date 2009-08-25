package com.github.sergiolopes.niceurls.consequences;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Route;
import com.github.sergiolopes.niceurls.results.Result;

public interface RouteConsequence {
	Result generateResult(Route route, ParamsContext context);
}
