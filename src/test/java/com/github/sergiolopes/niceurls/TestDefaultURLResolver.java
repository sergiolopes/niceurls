package com.github.sergiolopes.niceurls;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.sergiolopes.niceurls.consequences.DynamicRouteConsequence;
import com.github.sergiolopes.niceurls.consequences.MovedPermanentlyRouteConsequence;
import com.github.sergiolopes.niceurls.consequences.RedirectRouteConsequence;
import com.github.sergiolopes.niceurls.consequences.SkipToViewRouteConsequence;
import com.github.sergiolopes.niceurls.resolver.DefaultURLResolver;
import com.github.sergiolopes.niceurls.resolver.Result;
import com.github.sergiolopes.niceurls.resolver.Route;



public class TestDefaultURLResolver {

	private DefaultURLResolver resolver;
		
	@Before
	public void init () throws Exception {
		this.resolver = new DefaultURLResolver();
		this.resolver.addRoute(new Route("/contact/", "contact.form", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/;jsessionid=*", "/", new RedirectRouteConsequence()));
		this.resolver.addRoute(new Route("/", "home.page", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/trainnings/", "trainnings.listAll", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/trainnings/advantages/", "trainnings.seeAdvantages", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/trainnings/:{trainning}/", "trainnings.specific", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/blog/:{year}/:{month}/:{day}/", "posts.listByDate", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/contact.jsp", "/contact/", new RedirectRouteConsequence()));
		this.resolver.addRoute(new Route("/person/:{logic}/", "person.#{logic}", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/person/edit/:{person.id}/", "person.edit", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/admin/:{page}.jsp", "/admin/#{page}/", new MovedPermanentlyRouteConsequence()));
		this.resolver.addRoute(new Route("/moved/*", "http://test.com", new MovedPermanentlyRouteConsequence()));
		this.resolver.addRoute(new Route("/vraptor/", "http://vraptor.org", new RedirectRouteConsequence()));
		this.resolver.addRoute(new Route("/mycompany/", "/jsp/mycompany.jsp", new SkipToViewRouteConsequence()));
		this.resolver.addRoute(new Route("/mycompany/:{some}/", "/jsp/mycompany/#{some}.jsp", new SkipToViewRouteConsequence()));
		this.resolver.addRoute(new Route("/:{component}/add/", "#{component}.add", new DynamicRouteConsequence()));
		this.resolver.addRoute(new Route("/:{component}/:{logic}/", "#{component}.#{logic}", new DynamicRouteConsequence()));
	}
	
	@Test
	public void testRoutesSize() {
		Assert.assertEquals(17, resolver.getRoutes().size());
	}

	@Test
	public void testFirstAdded() {
		Route r = resolver.getRoutes().get(0);
		Assert.assertEquals(r.evaluateTo(null), "contact.form");
		Assert.assertEquals(DynamicRouteConsequence.class, r.getRouteConsequence().getClass());
	}
	
	@Test
	public void testContact() throws Exception {
		Result r = this.resolver.resolveURL("/contact/");
		Assert.assertEquals("contact", r.getComponentName());
		Assert.assertEquals("form", r.getLogicName());
	}
	
	@Test
	public void homepage() throws Exception {
		Result r = this.resolver.resolveURL("/");
		Assert.assertEquals("home", r.getComponentName());
		Assert.assertEquals("page", r.getLogicName());	}
	
	@Test
	public void testTreinamentos() throws Exception {
		Result r = this.resolver.resolveURL("/trainnings/");
		Assert.assertEquals("trainnings", r.getComponentName());
		Assert.assertEquals("listAll", r.getLogicName());
	}
	
	@Test
	public void testWildcard() throws Exception {
		Result r = this.resolver.resolveURL("/trainnings/fj11-java/");
		Assert.assertNotNull(r);
		Assert.assertEquals("trainnings", r.getComponentName());
		Assert.assertEquals("specific", r.getLogicName());
		Assert.assertEquals("fj11-java", r.getParameter("trainning"));
	}
	
	@Test
	public void testVariables() throws Exception {
		Result r = this.resolver.resolveURL("/blog/2008/05/01/");
		Assert.assertNotNull(r);
		Assert.assertEquals("posts", r.getComponentName());
		Assert.assertEquals("listByDate", r.getLogicName());
		Assert.assertEquals("2008", r.getParameter("year"));
		Assert.assertEquals("05", r.getParameter("month"));
		Assert.assertEquals("01", r.getParameter("day"));
	}
	
	@Test
	public void testRedirection() throws Exception {
		Result r = this.resolver.resolveURL("/contact.jsp");
		Assert.assertNotNull(r);
		Assert.assertEquals("VRaptorRedirector", r.getComponentName());
		Assert.assertEquals("movedTemporarily", r.getLogicName());
		Assert.assertEquals("/contact/", r.getParameter("uri"));
	}

	@Test
	public void testPathWithDots() throws Exception {
		Result r = this.resolver.resolveURL("/contactAjsp");
		Assert.assertNull(r);
	}
	
	@Test
	public void testOrder() throws Exception {
		Result r = this.resolver.resolveURL("/trainnings/advantages/");
		Assert.assertEquals("trainnings", r.getComponentName());
		Assert.assertEquals("seeAdvantages", r.getLogicName());
	}
	
	@Test
	public void notFound() throws Exception {
		Assert.assertNull(this.resolver.resolveURL("nothing"));
	}
	
	@Test
	public void testDynamicResults() throws Exception {
		Result r = this.resolver.resolveURL("/mycomponent/mylogic/");
		Assert.assertEquals("mycomponent", r.getComponentName());
		Assert.assertEquals("mylogic", r.getLogicName());
	}
	
	@Test
	public void testDynamicLogic() throws Exception {
		Result r = this.resolver.resolveURL("/person/add/");
		Assert.assertEquals("person", r.getComponentName());
		Assert.assertEquals("add", r.getLogicName());
	}

	@Test
	public void testDynamicLogic2() throws Exception {
		Result r = this.resolver.resolveURL("/person/list/");
		Assert.assertEquals("person", r.getComponentName());
		Assert.assertEquals("list", r.getLogicName());
	}
	
	@Test
	public void testDynamicComponent() throws Exception {
		Result r = this.resolver.resolveURL("/person/add/");
		Assert.assertEquals("person", r.getComponentName());
		Assert.assertEquals("add", r.getLogicName());
	}
	
	@Test
	public void testDirectToView() throws Exception {
		Result r = this.resolver.resolveURL("/mycompany/");
		Assert.assertNotNull(r);
		Assert.assertEquals("VRaptorRedirector", r.getComponentName());
		Assert.assertEquals("redirectToView", r.getLogicName());
		Assert.assertEquals("/jsp/mycompany.jsp", r.getParameter("uri"));
	}
	
	@Test
	public void testDirectToViewWithVariables() throws Exception {
		Result r = this.resolver.resolveURL("/mycompany/about/");
		Assert.assertNotNull(r);
		Assert.assertEquals("VRaptorRedirector", r.getComponentName());
		Assert.assertEquals("redirectToView", r.getLogicName());
		Assert.assertEquals("/jsp/mycompany/about.jsp", r.getParameter("uri"));
	}
	
	@Test
	public void testMovedPermanently() throws Exception {
		Result r = this.resolver.resolveURL("/admin/list.jsp");
		Assert.assertNotNull(r);
		Assert.assertEquals("VRaptorRedirector", r.getComponentName());
		Assert.assertEquals("movedPermanently", r.getLogicName());
		Assert.assertEquals("/admin/list/", r.getParameter("uri"));
	}
	
	@Test
	public void testMovedPermanentlyWithVariables() throws Exception {
		Result r = this.resolver.resolveURL("/moved/anything");
		Assert.assertNotNull(r);
		Assert.assertEquals("VRaptorRedirector", r.getComponentName());
		Assert.assertEquals("movedPermanently", r.getLogicName());
		Assert.assertEquals("http://test.com", r.getParameter("uri"));
	}

	@Test
	public void testVariableWithDot() {
		Result r = this.resolver.resolveURL("/person/edit/12/");
		Assert.assertEquals("person", r.getComponentName());
		Assert.assertEquals("edit", r.getLogicName());
		Assert.assertEquals("12", r.getParameter("person.id"));	
	}
	
	@Test
	public void testSemicolon() {
		Result r = this.resolver.resolveURL("/;jsessionid=*");
		System.out.println(r.getComponentName());
		System.out.println(r.getLogicName());
		System.out.println(r.getParameter("uri"));
	}
}
