package com.bh.timetracker.exception;


public class MediumException extends ExceptionAbstract {

	private static final long serialVersionUID = 1L;

	public MediumException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public MediumException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}