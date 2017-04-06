package com.anyonehelps.common.util;

import com.anyonehelps.exception.ServiceException;

public class ExceptionUtil {
	public static ServiceException handle2ServiceException(String message) {
		return new ServiceException(message);
	}

	public static ServiceException handle2ServiceException(Throwable e) {
		return new ServiceException(e);
	}

	public static ServiceException handle2ServiceException(String message,
			Throwable e) {
		return new ServiceException(message, e);
	}
}
