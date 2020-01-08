package com.smProject.interceptor;

import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.smProject.auth.Auth;
import com.smProject.auth.AuthUtil;
import com.smProject.common.Constant;
import com.smProject.util.MessageUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
	@Qualifier("config")
	private Properties config;
	
	@Autowired
	private MessageSourceAccessor mr;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

		boolean hasAuth = AuthUtil.hasAuth(request);

		logger.debug("AUTH CHECK : {}", hasAuth);
		logger.debug("RequestURL : {}", request.getRequestURL().toString());

		if (!hasAuth) {
			String returnUrl = request.getRequestURI();
			String queryString = request.getQueryString();
			if (StringUtil.isNotEmpty(queryString)) {
				returnUrl += "?" + queryString;
			}
			returnUrl = URLEncoder.encode(returnUrl, "UTF-8");

			MessageUtil.showAlert(request, response, mr.getMessage("msg.common.need.login"),
					"/login/login?returnUrl=" + returnUrl);
			return false;
		}

		try {
			// 메뉴 권한 체크
			setMenuAuth(request);
		} catch (BizException e) {
			MessageUtil.showAlert(request, response, e.getMessage(), config.getProperty("site.main"));
			return false;
		}

		return true;
	}

	/**
	 * 메뉴 권한 체크
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void setMenuAuth(HttpServletRequest request) throws Exception {

		// Ajax 요청일땐 return
		String url = request.getRequestURI();
		if (url.endsWith("Ajax")) {
			return;
		}

		Auth auth = AuthUtil.getAuth(request);
		if (Constant.ADMIN_AUTH.equals(auth.getAuth())) {
			return;
		}

		// 사용자 관리나 업체 관리로 URL 시작하면 관리자만 접근가능
		if (request.getServletPath().startsWith("/user/")) {
			throw new BizException(mr.getMessage("msg.common.auth.menu"));
		}

	}
}
