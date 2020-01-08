package com.smProject.auth;


import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class LoginManager implements HttpSessionBindingListener {
	private static LoginManager loginManager = null;
	private static Hashtable loginUsers = new Hashtable();

	private LoginManager() {
		super();
	}

	public static synchronized LoginManager getInstance() {
		if (loginManager == null) {
			loginManager = new LoginManager();
		}
		return loginManager;
	}

	// 아이디가 맞는지 체크
	public boolean isValid(String userID, String userPW) {
		return true; // 자세한 로직은 미구현
	}

	// 해당 세션에 이미 로그인 되있는지 체크
	public boolean isLogin(String sessionID) {
		boolean isLogin = false;
		Enumeration e = loginUsers.keys();
		String key = "";
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			if (sessionID.equals(key)) {
				isLogin = true;
			}
		}
		return isLogin;
	}

	// 중복 로그인 막기 위해 아이디 사용중인지 체크
	public boolean isUsing(String userID) {
		boolean isUsing = false;
		Enumeration e = loginUsers.keys();
		String key = "";
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			if (userID.equals(loginUsers.get(key))) {
				isUsing = true;
			}
		}
		return isUsing;
	}

	// 세션 생성
	public void setSession(HttpSession session, String userID) {
		// System.out.println("세션 생성");
		loginUsers.put(session.getId(), userID);
		session.setAttribute("login", this.getInstance());
	}

	// 세션 성립될 때
	public void valueBound(HttpSessionBindingEvent event) {
		// System.out.println("세션 성립될 때 event catch");
	}

	// 세션 끊길때
	public void valueUnbound(HttpSessionBindingEvent event) {
		// System.out.println("세션 끊김 event catch");
		loginUsers.remove(event.getSession().getId());
	}

	// 세션 ID로 로긴된 ID 구분
	public String getUserID(String sessionID) {
		return (String) loginUsers.get(sessionID);
	}

	// 현재 접속자수
	public int getUserCount() {
		return loginUsers.size();
	}
};
