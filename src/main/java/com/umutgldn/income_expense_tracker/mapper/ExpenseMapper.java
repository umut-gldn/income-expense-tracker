package com.umutgldn.income_expense_tracker.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.umutgldn.income_expense_tracker.dto.ExpenseRequest;
import com.umutgldn.income_expense_tracker.dto.ExpenseResponse;
import com.umutgldn.income_expense_tracker.model.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
	
	ExpenseResponse toExpenseResponse(Expense expense);
	
	Expense toExpense(ExpenseRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy=NullValuePropertyMappingStrategy.IGNORE)
	void updateExpenseFromRequest(ExpenseRequest request, @MappingTarget Expense expense);
	
}
