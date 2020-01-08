package com.smProject.auth;


import java.io.Serializable;

import com.smProject.util.StringUtil;

import lombok.Data;

@Data
public class Auth implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 사용자_ID */
	private String userId;

	/** 사용자_이름 */
	private String userNm;
	
	/** 직급 */
	private String position;

	/** 직급 */
	private String positionNm;
	
	/** 연락처 */
	private String phone;
	
	/** 프로필_파일 */
	private String prFile;
	
	/** 권한 01.일반사용자, 02.관리자 */
	private String auth;

	public boolean hasAuth() {
		return StringUtil.isNotEmpty(userId);
	}

}
