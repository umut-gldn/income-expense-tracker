package com.umutgldn.income_expense_tracker.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.umutgldn.income_expense_tracker.dto.TotalResponse;
import com.umutgldn.income_expense_tracker.model.User;
import com.umutgldn.income_expense_tracker.repository.ExpenseRepository;
import com.umutgldn.income_expense_tracker.repository.IncomeRepository;
import com.umutgldn.income_expense_tracker.service.ReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

	private final IncomeRepository incomeRepository;
	private final ExpenseRepository expenseRepository;

	
	@Override
	public TotalResponse getTotalByUser(User user) {
		BigDecimal totalIncome=incomeRepository.sumAmountByUser(user);
		BigDecimal totalExpense=expenseRepository.sumAmountByUser(user);
		BigDecimal balance=totalIncome.subtract(totalExpense);
		return new TotalResponse(totalIncome,totalExpense,balance);
		
	}

}
