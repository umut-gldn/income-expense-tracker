package com.umutgldn.income_expense_tracker.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
		String message,
		int status,
		LocalDateTime timestamp) {}
