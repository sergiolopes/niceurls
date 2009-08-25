package com.github.sergiolopes.niceurls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;


public class TestRegex {

	@Test
	public void test1() throws Exception {
		Pattern pattern = Pattern.compile("/treinamentos/(.*)/");
		Matcher matcher = pattern.matcher("/treinamentos/fj11/");
		
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals(matcher.group(1), "fj11");
	}
		
	@Test
	public void test2() throws Exception {
		Pattern pattern = Pattern.compile("/treinamentos/(.*)/(.*)/");
		Matcher matcher = pattern.matcher("/treinamentos/fj11/param2/");
		
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals(matcher.group(1), "fj11");
		Assert.assertEquals(matcher.group(2), "param2");
	}
	
	@Test
	public void test3() throws Exception {
		Pattern pattern = Pattern.compile("/treinamentos/:(.*)/");
		Matcher matcher = pattern.matcher("/treinamentos/:treinamento/");
		
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals(matcher.group(1), "treinamento");
	}

	@Test
	public void test4() throws Exception {
		Pattern pattern = Pattern.compile(".*:\\{([A-Za-z]+)\\}.*");
		Matcher matcher = pattern.matcher("tetete/:{abcAB}/$%890:");
		
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals("abcAB", matcher.group(1));
	}

	@Test
	public void test5() throws Exception {
		String s = "/treinamentos/:sigla/:numero/:resto/aa";
		
		Pattern pattern = Pattern.compile("[^:]*:([a-z]+)");
		Matcher matcher = pattern.matcher(s);
		
		while (!matcher.hitEnd()) {
			if(matcher.find()) {
				break;
			}
			System.out.println(matcher.group(1));
		}
		
		String nova = s.replaceAll(":[a-z]+", "(.*)");
	}
	
}
