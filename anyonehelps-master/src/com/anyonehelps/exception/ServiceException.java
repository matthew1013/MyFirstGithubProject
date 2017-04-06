package com.anyonehelps.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1947090579471240235L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
