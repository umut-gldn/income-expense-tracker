package com.umutgldn.income_expense_tracker.exception;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
