package com.bh.timetracker.exception;

public class CategoryNotFoundException extends ExceptionAbstract {

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public CategoryNotFoundException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}