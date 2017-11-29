package com.bh.timetracker.exception;

public class TaskException extends ExceptionAbstract {

	private static final long serialVersionUID = 1L;

	public TaskException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public TaskException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}