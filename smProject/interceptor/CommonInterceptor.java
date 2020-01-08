package com.smProject.interceptor;


import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.smProject.auth.Auth;
import com.smProject.auth.AuthUtil;
import com.smProject.common.Constant;
import com.smProject.common.annotation.Access;
import com.smProject.common.annotation.AccessRole;


public class CommonInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

	@Autowired
	@Qualifier("config")
	private Properties config;

	@Autowired
	private MessageSourceAccessor mr;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		logger.info("################ REQUEST ################");
		// logger.info("# Server = {}", config.getProperty("server.id"));
		logger.info("# Host           	= {}", request.getRemoteHost());
		logger.info("# RequestURI     	= {}", request.getRequestURI());
		logger.debug("# RequestURL     	= {}", request.getRequestURL().toString());
		logger.debug("# QueryString    	= {}", request.getQueryString());
		logger.debug("# ContextPath    	= {}", request.getContextPath());
		logger.debug("# ServletPath    	= {}", request.getServletPath());
		logger.info("# Method         	= {}", request.getMethod());
		logger.info("# User-Agent     	= {}", request.getHeader("user-agent"));
		logger.debug("# Port           	= {}", request.getServerPort());
		logger.debug("# sessionId		= {}", request.getSession().getId());
		logger.debug("# Accept-Language	= {}", request.getHeader("Accept-Language"));
		logger.info("########################################\n");

		// 디바이스 정보 SET
		setDevice(request);

		// 공통 사용 변수 SET
		setCommonRequestAttribute(request);

		// 로그인 정보 SET
		setLoginInfo(request, response);
		
		return true;
	}

	private void setLoginInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그아웃 요청이면 return
		if (request.getRequestURI().indexOf("/login/logout") == 0) {
			return;
		}

		Auth auth = AuthUtil.getAuth(request);
		boolean hasAuth = auth.hasAuth();
		request.setAttribute("IS_LOGIN", hasAuth);
		request.setAttribute(AuthUtil.AUTH_KEY, auth);

	}

	/**
	 * 현재 접속 디바이스 setAttribute
	 *
	 * @param request
	 * @return
	 */
	private void setDevice(HttpServletRequest request) {
		String viewPath = Constant.DEVICE_PC_VIEW_PATH;

		// Device device = (Device) request.getAttribute("currentDevice");
		Device device = DeviceUtils.getCurrentDevice(request);
		logger.debug("device.isNormal() : {}", device.isNormal());
		logger.debug("device.isMobile() : {}", device.isMobile());
		logger.debug("device.isTablet() : {}", device.isTablet());

		SitePreference sitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
		viewPath = (sitePreference == SitePreference.MOBILE) ? Constant.DEVICE_MOB_VIEW_PATH : viewPath;
		logger.debug("VIEW_PATH : {}", viewPath);

		// request.setAttribute("VIEW_PATH", agent);
		// 모바일 페이지 없음..
		request.setAttribute("VIEW_PATH", Constant.DEVICE_PC_VIEW_PATH);
	}

	/**
	 * 공통 변수 SET - request attribute
	 *
	 * @param request
	 * @throws Exception
	 */
	private void setCommonRequestAttribute(HttpServletRequest request) throws Exception {
		String staticUrl = config.getProperty("static.url");
		String siteDomain = config.getProperty("site.domain");
		String siteMain = config.getProperty("site.main");

		request.setAttribute("STATIC_COMMON_URL", staticUrl);
		request.setAttribute("SITE_DOMAIN", siteDomain);
		request.setAttribute("SITE_MAIN", siteMain);
		request.setAttribute("STATIC_URL", staticUrl + "/" + request.getAttribute("VIEW_PATH"));
	}


	/**
	 * 컨트롤러 접근 제한 체크
	 *
	 * @param request
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	private boolean isNeedAuthController(HttpServletRequest request, Object handler) throws Exception {

		Method method = getUriMappingMethod(request, handler);
		// Access 어노테이션이 없으면 로그인 체크 안함
		if (method == null || method.getAnnotation(Access.class) == null) {
			return false;
		}

		AccessRole accessRole = method.getAnnotation(Access.class).value();
		logger.debug("authControllerCheck accessRole : {}", accessRole.name());
		logger.debug("authControllerCheck  {}", (accessRole == AccessRole.LOGIN_USER));

		return (accessRole == AccessRole.LOGIN_USER);
	}

	/**
	 * 어노테이션 정보 조회
	 *
	 * @param request
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Method getUriMappingMethod(HttpServletRequest request, Object handler) throws Exception {
		try {
			String requestUri = request.getRequestURI();
			HandlerMethod hMethod = (HandlerMethod) handler;
			Class clazz = hMethod.getMethod().getDeclaringClass();

			String[] pPath = { "" };
			RequestMapping clazzRequestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
			if (clazzRequestMapping != null) {
				pPath = clazzRequestMapping.value();
			}

			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping == null) {
					continue;
				}

				String[] mappingUris = requestMapping.value();
				for (String path : pPath) {
					for (String uri : mappingUris) {
						if (requestUri.equals(path + uri)) {
							return method;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
