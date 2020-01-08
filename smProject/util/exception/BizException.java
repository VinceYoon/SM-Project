package com.smProject.util.exception;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

	public BizException() {
		this.code = "";
		this.message = "";
	}

	public BizException(String message) {
		this();
		this.message = message;
	}

	public BizException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return this.code;
	}
}
