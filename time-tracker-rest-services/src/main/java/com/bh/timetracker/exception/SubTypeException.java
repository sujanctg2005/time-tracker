package com.bh.timetracker.exception;

public class SubTypeException extends ExceptionAbstract {

	private static final long serialVersionUID = 1L;

	public SubTypeException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public SubTypeException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}