package com.bh.timetracker.exception;

public abstract class ExceptionAbstract extends  Exception{

	private static final long serialVersionUID = -4696310704995351156L;
	protected String errorCode;
	protected String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
