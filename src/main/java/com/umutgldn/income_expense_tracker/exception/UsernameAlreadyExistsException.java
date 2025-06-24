package com.umutgldn.income_expense_tracker.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
	
	public UsernameAlreadyExistsException (String message) {
		super(message);
	}

}
