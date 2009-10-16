package com.github.sergiolopes.niceurls.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

		parseReader(new FileReader(file));
	}

	private void parseReader(Reader reader) throws IOException {
		Scanner s = new Scanner(reader);

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
			parseIncludedFile(line);
		} else if (!isComment(line)) {
			parseRouteLine(line);
		}
	}

	private void parseRouteLine(String line) {
		for (RouteType routeType : RouteType.values()) {
			if (line.indexOf(routeType.getSeparator()) > 0) {
				
				String[] split = line.split("\\s*" + routeType.getSeparator()	+ "\\s*");
				resolver.addRoute(new Route(split[0], split.length == 1? null: split[1], routeType));

				if (logger.isTraceEnabled())
					logger.trace("NiceRoute found: " + split[0] + " to " + (split.length == 1? "": split[1]));
				break;
			}
		}
	}

	private void parseIncludedFile(String line) throws IOException {
		String otherFile = line.substring("#include".length()).trim();
		new RoutesParser(resolver).parseFile(this.path + "/" + otherFile);
	}

}
