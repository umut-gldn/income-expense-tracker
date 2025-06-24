package com.umutgldn.income_expense_tracker.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException{
 public InvalidJwtAuthenticationException(String message) {
		super(message);
	}

}
