package com.hcl.guvi.capstonproject.eventmanagement.exception;
public class ResourceNotFoundException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

