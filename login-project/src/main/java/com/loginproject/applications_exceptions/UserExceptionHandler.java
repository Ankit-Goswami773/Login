package com.loginproject.applications_exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.loginproject.common.CommonResponse;


@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CommonLoginException.class)
	public ResponseEntity<CommonResponse> employeeNotFound(CommonLoginException notFound) {
		CommonResponse message = new CommonResponse();
		message.setStatusCode(notFound.getStatusCode());
		message.setMessage(notFound.getMessage());
		message.setData("");
		return new ResponseEntity<>(message, notFound.getStatus());
	}
}
