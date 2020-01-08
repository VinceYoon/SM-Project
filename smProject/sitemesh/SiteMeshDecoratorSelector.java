package com.smProject.sitemesh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sitemesh.config.PathBasedDecoratorSelector;
import org.sitemesh.content.Content;
import org.sitemesh.content.ContentChunk;
import org.sitemesh.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteMeshDecoratorSelector extends PathBasedDecoratorSelector<WebAppContext> {
	private static final Logger logger = LoggerFactory.getLogger(SiteMeshDecoratorSelector.class);

	public static final String DEFAULT = "default";
	public static final String NONE = "none";

	private final Map<String, String> mappings = new HashMap<String, String>();

	private boolean useCookie = false;
	private String cookieName = "DECORATOR";

	@Override
	public String[] selectDecoratorPaths(Content content, WebAppContext siteMeshContext) throws IOException {
		/*
		 * If page contains <meta name='decorator'
		 * content='/some/dec.html,/another/dec.html'>
		 */
		ContentChunk decoratorMetaTag = content.getExtractedProperties().getChild("meta.decorator");
		if (decoratorMetaTag.hasValue()) {
			logger.debug("decoratorMetaTag : {}", decoratorMetaTag.getValue());
			return decoratorMetaTag.getValue().split(",");
		}

		// request path 기준으로 매핑된 decorator path를 가져온다
		String[] pathDecorator = super.getPathMapper().get(siteMeshContext.getPath());
		if (pathDecorator != null) {
			logger.debug("pathDecorator : {}", (Object[]) pathDecorator);
			return pathDecorator;
		}

		String decorator;

		decorator = (String) siteMeshContext.getRequest().getAttribute("__sitemesh__decorator");
		logger.debug("getAttribute __sitemesh__decorator : {}", decorator);
		if ("none".equals(decorator)) {
			decorator = mappings.get(NONE);
		} else {
			decorator = mappings.get(DEFAULT);
		}

		return decorator.split(",");

		/*
		 * 디바이스 또는 쿠키 값을 기준으로 사이트 설정값을 가져온다. 쿠키값 우선 처리. 쿠키 설정 예시
		 * http://domain/?site_preference=mobile / normal / tablet
		 */
		// final SitePreference sitePreference = (SitePreference)
		// siteMeshContext.getRequest()
		// .getAttribute(SitePreferenceHandler.CURRENT_SITE_PREFERENCE_ATTRIBUTE);

	}

	public void putMapping(String pattern, String value) {
		if (value != null) {
			mappings.put(pattern, value);
		}
	}

	public boolean isUseCookie() {
		return useCookie;
	}

	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
}
