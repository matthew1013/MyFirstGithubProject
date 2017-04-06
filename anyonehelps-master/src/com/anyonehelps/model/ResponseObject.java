package com.anyonehelps.model;

import java.io.Serializable;


import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.anyonehelps.common.constants.ResponseCode;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ResponseObject<T> implements Serializable {

	private static final long serialVersionUID = -7019752250983810104L;
	private String code;
	private String message;
	private T data;

	public ResponseObject() {
		super();
		this.code = ResponseCode.SUCCESS_CODE;
	}

	public ResponseObject(String code) {
		super();
		this.code = code;
	}

	public ResponseObject(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResponseObject(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseObject other = (ResponseObject) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseObject [code=" + code + ", data=" + data + ", message=" + message + "]";
	}

}
