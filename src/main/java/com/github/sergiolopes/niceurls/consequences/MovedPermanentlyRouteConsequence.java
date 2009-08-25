package com.github.sergiolopes.niceurls.consequences;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Result;
import com.github.sergiolopes.niceurls.resolver.Route;

/**
 * A redirection consequence points to a uri and should use our VRaptorRedirector
 * @author sergio
 *
 */
public class MovedPermanentlyRouteConsequence implements RouteConsequence {

	private static final String REDIRECTOR_COMPONENT = "VRaptorRedirector";
	private static final String REDIRECTOR_LOGIC = "movedPermanently";

	public Result generateResult(Route route, ParamsContext context) {
		Result result = new Result();
		result.setParamsContext(context);
		
		result.setComponentName(REDIRECTOR_COMPONENT);
		result.setLogicName(REDIRECTOR_LOGIC);
		context.addParameter("uri", route.evaluateTo(context));

		return result;
	}

}
