package com.umutgldn.income_expense_tracker.service;

import java.util.List;

import com.umutgldn.income_expense_tracker.dto.IncomeRequest;
import com.umutgldn.income_expense_tracker.dto.IncomeResponse;

public interface IncomeService {
	
	IncomeResponse addIncome(String username, IncomeRequest request);
	
	List<IncomeResponse> getIncomes(String username);
	
	IncomeResponse updateIncome(String username, Long id,IncomeRequest request);
	
	void deleteIncome(String username, Long incomeId);

}
