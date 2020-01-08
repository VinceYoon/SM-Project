package com.smProject.sitemesh;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	private static final Logger logger = LoggerFactory.getLogger(SiteMeshFilter.class);

	public static final String SITE_MESH_DECORATOR_KEY = "__sitemesh__decorator";

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		logger.debug("applyCustomConfiguration");

		final String path = "/WEB-INF/views/decorators/";
		SiteMeshDecoratorSelector decoratorSelector = new SiteMeshDecoratorSelector();

		decoratorSelector.putMapping(SiteMeshDecoratorSelector.DEFAULT, path + "w/default.jsp");
		decoratorSelector.putMapping(SiteMeshDecoratorSelector.NONE, path + "none.jsp");

		decoratorSelector.setUseCookie(true);

		// Assign
		builder.setCustomDecoratorSelector(decoratorSelector).addExcludedPath("/resources/*")
				.addExcludedPath("/monitor/*").addExcludedPath("/smartEditor/*").addExcludedPath("*Ajax*")
				.addExcludedPath("/cxf*").addExcludedPath("/*_index.html").addExcludedPath("/index.jsp")
				.addExcludedPath("*/logout.jsp").addExcludedPath("/csr/login.jsp").addExcludedPath("/error/error.jsp");

	}
}
