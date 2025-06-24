package com.umutgldn.income_expense_tracker.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalResponse {

	private BigDecimal totalIncome;
	
	private BigDecimal totalExpense;
	
	private BigDecimal balance;
}
