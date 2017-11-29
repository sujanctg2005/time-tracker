package com.bh.timetracker.exception;

public class TaskTypeNotFoundException extends ExceptionAbstract {

	private static final long serialVersionUID = 1L;

	public TaskTypeNotFoundException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public TaskTypeNotFoundException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}