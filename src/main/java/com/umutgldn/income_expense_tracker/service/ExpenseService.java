package com.umutgldn.income_expense_tracker.service;

import java.util.List;

import com.umutgldn.income_expense_tracker.dto.ExpenseRequest;
import com.umutgldn.income_expense_tracker.dto.ExpenseResponse;

public interface  ExpenseService {
	
	ExpenseResponse addExpense(String username,ExpenseRequest request);
	
	List<ExpenseResponse> getExpenses(String username);
	
	ExpenseResponse updateExpense(Long id, ExpenseRequest request, String username);
	
	void deleteExpense(String username,Long id);

}
