package com.loginproject.applications_exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CommonLoginException extends RuntimeException {

	private static final long serialVersionUID = -5226208827344858886L;

	private final int statusCode;
	private final HttpStatus status;

	public CommonLoginException(String message, int statusCode, HttpStatus httpStatus) {
		super(message);
		this.statusCode = statusCode;
		this.status = httpStatus;
	}
}
