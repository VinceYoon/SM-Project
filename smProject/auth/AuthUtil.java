package com.smProject.auth;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smProject.util.CookieUtil;
import com.smProject.util.StringUtil;


// TODO 로그인 쿠키연동 주석처리 - 로그인 쿠키 사용시 수정
public class AuthUtil {

	public static final String COOKIE_KEY = "CSR_AUTH";
	public static final String COOKIE_SAVE_ID = "CSR_SAVE_ID";
	public static final String SESSION_KEY = "SESS_AUTH";
	public static final String AUTH_KEY = "AUTH";
	public static int SESSION_TIME = 60 * 60 * 2;

	/**
	 * 로그인 정보 조회
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Auth getAuth(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Auth auth = (Auth) session.getAttribute(SESSION_KEY);
		return auth != null ? auth : new Auth();
	}

	/**
	 * 로그인 정보 조회 세션 정보가 없을 경우 쿠키 정보를 세션에 담는다.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Auth getAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Auth auth = getAuth(request);

		return auth != null ? auth : new Auth();
	}

	/**
	 * 로그인 정보 set
	 *
	 * @param request
	 * @param auth
	 * @throws Exception
	 */
	public static void setAuth(HttpServletRequest request, Auth auth) throws Exception {
		removeAuth(request);
		HttpSession session = request.getSession();

		if (auth == null) {
			auth = new Auth();
		}
		session.setAttribute(SESSION_KEY, auth);
		session.setMaxInactiveInterval(SESSION_TIME);
	}

	/**
	 * 로그인 session 삭제
	 *
	 * @param request
	 * @throws Exception
	 */
	public static void removeAuth(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_KEY);
	}

	/**
	 * 세션 및 쿠및 등록
	 *
	 * @param request
	 * @param response
	 * @param auth
	 * @throws Exception
	 */
	public static void setAuth(HttpServletRequest request, HttpServletResponse response, Auth auth) throws Exception {
		setAuth(request, auth);
	}

	/**
	 * 로그인 여부
	 *
	 * @param request
	 * @throws Exception
	 */
	public static boolean hasAuth(HttpServletRequest request) throws Exception {
		Auth auth = getAuth(request);

		return StringUtil.isNotEmpty(auth.getUserId());
	}

	/**
	 * 세션 및 쿠키 삭제
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void removeAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		removeAuth(request);
	}

	/**
	 * 쿠키 토큰값 반환
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getAuthToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieUtil cu = new CookieUtil(request, response);
		return cu.getCookie(COOKIE_KEY);
	}

	/**
	 * 토큰 스트링으로 Auth 반환
	 *
	 * @param authToken
	 * @return
	 * @throws Exception
	 */
	public static Auth parseAuthToken(String authToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String[] auths = StringUtil.split(authToken, ",");
		for (String row : auths) {
			String[] data = StringUtil.split(row, "=");
			String key = data[0].trim();
			String value = data[1].trim();
			map.put(key, value);
		}

		Auth auth = new Auth();

		return auth;
	}

	/*
	 * private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For",
	 * "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
	 * "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
	 * "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };
	 */
	private static final String[] HEADERS_TO_TRY = { "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED",
			"HTTP_X_CLUSTER_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED" };

	
	public static String getClientIpAddress(HttpServletRequest request) {

		return request.getRemoteAddr();
	}

}
