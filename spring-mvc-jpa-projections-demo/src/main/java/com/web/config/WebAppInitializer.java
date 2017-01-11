package com.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer{
	

	public void onStartup(ServletContext servletContext) throws ServletException {
		 
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        
        ctx.register(WebApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        
        /*servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class)
		.addMappingForUrlPatterns(null, false, "/");*/
 
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        
        
    }
	
}
