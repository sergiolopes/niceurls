package com.github.sergiolopes.niceurls;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.parser.RoutesParser;
import com.github.sergiolopes.niceurls.resolver.DefaultURLResolver;
import com.github.sergiolopes.niceurls.resolver.URLResolver;
import com.github.sergiolopes.niceurls.results.Result;
import com.github.sergiolopes.niceurls.servlet.NiceHttpServletRequest;

public class NiceURLsFilter implements Filter{

	private static final Logger logger = Logger.getLogger(NiceURLsFilter.class);
	private static final String DEFAULT_NICEURL_FILENAME = "/niceurl.routes";
	private static final String ROUTES_FILENAME_PARAM = "niceurls_file";

	private String contextName;
	private URLResolver urlResolver;

	public void init(final FilterConfig fc) throws ServletException {
		if (logger.isTraceEnabled()) logger.trace("Filter init");
		
		String configFile = resolveConfigFile(fc.getServletContext());
		
        // init config file
        this.urlResolver = new DefaultURLResolver();
        RoutesParser parser = new RoutesParser(urlResolver);
        
        try {
			parser.parseFile(configFile);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		// caches some strings
		this.contextName = fc.getServletContext().getContextPath();
	}

	private String resolveConfigFile(ServletContext servletContext) {
		String filenameParam = servletContext.getInitParameter(ROUTES_FILENAME_PARAM);
		return getClass()
		       .getResource(filenameParam != null? filenameParam : DEFAULT_NICEURL_FILENAME)
		       .getFile();
	}
	
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {

		// use our magical request
		NiceHttpServletRequest request = new NiceHttpServletRequest((HttpServletRequest) servletRequest);
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// finds uri
		String uri = extractURI(request);
		if (logger.isDebugEnabled()) logger.debug("Hit: "+ uri);

		// what's the result for the url
		Result result = this.urlResolver.resolveURL(uri);
		
		// not found
		if (result == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// add niceurl parameters to ${params}
		Map<String, String> parameters = (Map<String, String>) request.getAttribute("NiceURLVRaptorPluginParameterMap");
		parameters.putAll(result.getParameters());			

		result.execute(request, response);
	}

	private String extractURI(NiceHttpServletRequest request) {
		return request.getRequestURI().substring(this.contextName.length());
	}

	public void destroy() {
	}
}
