package com.umutgldn.income_expense_tracker.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidJwt(InvalidJwtAuthenticationException ex) {
	    return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
		return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex){
		return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex){
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(ExpenseNotFoundException.class)
	public ResponseEntity<String> handleExpenseNotFound(ExpenseNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(IncomeNotFoundException.class)
	public ResponseEntity<String> handleIncomeNotFound(IncomeNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
		Map<String, String> fieldErrors=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName=((FieldError)error).getField();
			String errorMessage=error.getDefaultMessage();
			fieldErrors.put(fieldName, errorMessage);
		});
		String combinedMessage=fieldErrors.entrySet().stream()
				.map(entry->entry.getKey()+": "+entry.getValue())
				.reduce((m1,m2)->m1+"; "+m2)
				.orElse("Validation failed");
		return buildErrorResponse(combinedMessage, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex){
		return buildErrorResponse("Internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	private ResponseEntity<ErrorResponse> buildErrorResponse(String message,HttpStatus status){
		ErrorResponse errorResponse=new ErrorResponse(message, status.value(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponse,status);
	}
}
