package com.smProject.vo;


import java.util.Date;

import lombok.Data;

@Data
public class ResultVO {

	public final static String SUCCESS = "success";
	public final static String FAIL = "fail";

	/** 처리 결과 */
	private String status;
	/** 처리 결과 코드 */
	private String code;
	/** 메시지 */
	private String msg;
	/** 날짜 */
	private String date;

	/**
	 * 생성자
	 */
	public ResultVO() {
		this.status = SUCCESS;
		this.code = "00";
		this.msg = "SUCCESS";
		setNowDate();
	}

	/**
	 * 생성자
	 *
	 * @param status
	 *            처리 상태
	 */
	public ResultVO(String status) {
		this.status = status;
		if (SUCCESS.equals(status)) {
			this.code = "00";
			this.msg = "SUCCESS";
		} else if (FAIL.equals(status)) {
			this.code = "10";
			this.msg = "FAIL";

		} else if ("exception".equals(status)) {
			this.code = "99";
			this.msg = "Exception";
		}
		setNowDate();
	}

	/**
	 * 생성자
	 *
	 * @param status
	 *            처리 상태
	 * @param msg
	 *            메시지
	 */
	public ResultVO(String status, String msg) {
		this.status = status;
		this.msg = msg;
		if (FAIL.equals(status)) {
			this.code = "10";

		} else if ("exception".equals(status)) {
			this.code = "99";
		}
		setNowDate();
	}

	/**
	 * 생성자
	 *
	 * @param status
	 *            처리 상태
	 * @param code
	 *            처리 코드
	 * @param msg
	 *            메시지
	 */
	public ResultVO(String status, String code, String msg) {
		this.status = status;
		this.code = code;
		this.msg = msg;
		setNowDate();
	}

	public void setNowDate() {
		date = new Date().toString();
	}

}
