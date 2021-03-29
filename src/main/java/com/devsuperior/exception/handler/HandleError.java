package com.devsuperior.exception.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HandleError {
	
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    @JsonIgnore
    private HttpStatus httpStatus;
    
    public HandleError(String message, HttpStatus httpStatus, String path) {
        this.timestamp = Instant.now();
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.message = message;
        this.path = path;
    }

	public Instant getTimestamp() {
		return timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}
	
	public void  setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	} 
	
}
