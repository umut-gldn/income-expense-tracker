package com.umutgldn.income_expense_tracker.exception;

public class ExpenseNotFoundException extends RuntimeException {
	public ExpenseNotFoundException(String message) {
		super(message);
	}

}
