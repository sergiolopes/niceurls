package com.github.sergiolopes.niceurls.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.github.sergiolopes.niceurls.resolver.Route;
import com.github.sergiolopes.niceurls.resolver.URLResolver;


/**
 * Parses a .routes file and adds routes to an URLResolver
 */
public class RoutesParser {

	private final static Logger logger = Logger.getLogger(RoutesParser.class);
	private final URLResolver resolver;
	private String path;

	public RoutesParser(URLResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Parses .routes file
	 * 
	 * @param url
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void parseFile(String fileName) throws IOException {
		if (logger.isDebugEnabled())
			logger.debug("Parsing routes file " + fileName);
		
		File file = new File(fileName);
		this.path = file.getParent();

		Scanner s = new Scanner(file);

		while (s.hasNextLine()) {
			parseLine(s.nextLine().trim());
		}

		s.close();
	}

	private boolean isComment(String line) {
		return line.length() == 0 || line.charAt(0) == '#';
	}

	private void parseLine(String line) throws IOException {
		if (line.startsWith("#include")) {
			String otherFile = line.substring("#include".length()).trim();
			parseFile(this.path + "/" + otherFile);
			return;
		}
		
		if (isComment(line)) return;
		
		for (RouteDefinition rd : RouteDefinition.values()) {
			if (line.indexOf(rd.getSeparator()) > 0) {
				
				String[] split = line.split("\\s*" + rd.getSeparator()	+ "\\s*");
				resolver.addRoute(new Route(split[0], split.length == 1? null: split[1], rd.getRouteConsequence()));

				if (logger.isTraceEnabled())
					logger.trace("NiceRoute found: " + split[0] + " to " + (split.length == 1? "": split[1]));
				break;
			}
		}
	}

}
