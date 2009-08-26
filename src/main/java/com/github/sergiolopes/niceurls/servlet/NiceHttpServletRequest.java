package com.github.sergiolopes.niceurls.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.github.sergiolopes.niceurls.resolver.ParamsContext;

/**
 * Overrides all parameter methods to get values from our special Map 
 */
public class NiceHttpServletRequest extends HttpServletRequestWrapper {

	// special Map to extend params
	private final Map<String, String> urlParameters = new HashMap<String, String>();
	
	public NiceHttpServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String parameter = super.getParameter(name);
		if (parameter == null)
			parameter = urlParameters.get(name);
		return parameter;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getParameterNames() {
		final Enumeration normal = super.getParameterNames();
		final Iterator<String> url = urlParameters.keySet().iterator();

		return new Enumeration() {

			public boolean hasMoreElements() {
				return normal.hasMoreElements() || url.hasNext();
			}

			public Object nextElement() {
				if (normal.hasMoreElements())
					return normal.nextElement();
				
				if (url.hasNext())
					return url.next();
				
				return null;
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getParameterMap() {
		Map map = new HashMap(urlParameters);
		map.putAll(super.getParameterMap());
		return map;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String[] paramValues = super.getParameterValues(name);
		if (paramValues == null)
			paramValues = new String[]{urlParameters.get(name)};
		return paramValues;
	}
	
	public void putParameters(ParamsContext params) {
		this.urlParameters.putAll(params.getParametersAsMap());
	}
}
