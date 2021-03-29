package com.devsuperior.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.exception.ResourceNotFoundException;

@ControllerAdvice
public class DsClientExcpetionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<HandleError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
		HandleError handleError = new HandleError(e.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
		handleError.setError("Resource not found");

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handleError);
	}
	
}
