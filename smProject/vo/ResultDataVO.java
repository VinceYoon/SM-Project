package com.smProject.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ResultDataVO<T> extends ResultVO {
	private T resultData;

	/**
	 * 생성자(기본) : 성공
	 */
	public ResultDataVO() {
		super();
	}

	/**
	 * 생성자(메시지 기본)
	 *
	 * @param status
	 * @param resultData
	 */
	public ResultDataVO(String status, T resultData) {
		super(status);
		this.resultData = resultData;
	}

	/**
	 * 상황별 생성자
	 *
	 * @param status
	 * @param msg
	 * @param resultData
	 */
	public ResultDataVO(String status, String msg, T resultData) {
		super(status, msg);
		this.resultData = resultData;
	}

	/**
	 * 상황별 생성자
	 *
	 * @param status
	 * @param code
	 * @param msg
	 * @param resultData
	 */
	public ResultDataVO(String status, String code, String msg, T resultData) {
		super(status, code, msg);
		this.resultData = resultData;
	}

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}
}
