package com.bh.timetracker.utility;

import com.bh.timetracker.exception.Error;
import com.bh.timetracker.exception.ExceptionAbstract;

public class Payload<T> {
	private T data;
	private Error error;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public Payload(T data, ExceptionAbstract exception) {
		super();
		this.data = data;
		this.error = convertToError(exception);
	}

	public Payload(ExceptionAbstract exception) {
		super();
		this.error = convertToError(exception);
	}

	public Payload(T data) {
		super();
		this.data = data;

	}

	private Error convertToError(ExceptionAbstract exception) {

		return new Error(exception.getErrorCode(), exception.getErrorMessage());

	}
}
