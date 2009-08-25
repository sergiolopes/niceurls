package com.github.sergiolopes.niceurls.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.parser.RoutesParser;
import com.github.sergiolopes.niceurls.resolver.DefaultURLResolver;
import com.github.sergiolopes.niceurls.resolver.Result;
import com.github.sergiolopes.niceurls.resolver.URLResolver;


/**
 * Filters all requests. If the request is to an existing file, process that file.
 * Otherwise, delegates to VRaptorServlet.
 */
public class NiceURLFilter implements Filter{

	private static final Logger logger = Logger.getLogger(NiceURLFilter.class);
	
	private String rootFolder;
	private String contextName;

	private URLResolver urlResolver;

	public void init(final FilterConfig fc) throws ServletException {
		if (logger.isTraceEnabled()) logger.trace("Filter init");
		
        // init config file
        this.urlResolver = new DefaultURLResolver();
        RoutesParser parser = new RoutesParser(urlResolver);
        
        // TODO let user change file name
        String configFile = this.getClass().getResource("/niceurl.routes").getFile();
        try {
			parser.parseFile(configFile);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		// caches some strings
		this.rootFolder = getRootFolder();
		this.contextName = fc.getServletContext().getContextPath();
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

		File f = new File(rootFolder + uri);
		
		// TODO cache existent files
		if (f.exists() && !uri.equals("/")) {
			if (logger.isTraceEnabled()) logger.trace("File exists, redirecting");
			filterChain.doFilter(request, response);
		} else {

			// what's the result for the url
			Result result = this.urlResolver.resolveURL(uri);
			
			// not found
			if (result == null) {
				if (uri.endsWith(".logic")) {
					response.sendError(404, uri + " not found");
					return;
				}
				filterChain.doFilter(request, response);
				return;
			}
			
			// add niceurl parameters to ${params}
			Map<String, String> parameters = (Map<String, String>) request.getAttribute("NiceURLVRaptorPluginParameterMap");
			parameters.putAll(result.getParameters());			
			
			// TODO change .logic
			String to = "/" + result.getComponentName() + "." + result.getLogicName() + ".logic"; 
			if (logger.isTraceEnabled()) logger.trace("Redirecting to VRaptor Servlet: " + to);
			request.getRequestDispatcher(to).forward(request, response);
		}
	}

	private String extractURI(NiceHttpServletRequest request) {
		return request.getRequestURI().substring(this.contextName.length());
	}

	private String getRootFolder() {
		return this.getClass().getResource("/").getFile().replaceAll("WEB-INF/.*/", "");
	}

	public void destroy() {
	}
}
