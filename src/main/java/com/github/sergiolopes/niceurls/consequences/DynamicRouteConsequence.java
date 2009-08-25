package com.github.sergiolopes.niceurls.consequences;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;
import com.github.sergiolopes.niceurls.resolver.Result;
import com.github.sergiolopes.niceurls.resolver.Route;

/**
 * Allow user to use parameter variables in consequence definitions.
 * Ex.
 * 		#component.#logic 
 */
public class DynamicRouteConsequence implements RouteConsequence {

	public Result generateResult(Route route, ParamsContext context) {
		Result result = new Result();
		result.setParamsContext(context);
				
		String finalConsequence = route.evaluateTo(context);
		String[] split = finalConsequence.split("\\.");
		result.setComponentName(split[0]);
		result.setLogicName(split[1]);
		
		return result;
	}


}
