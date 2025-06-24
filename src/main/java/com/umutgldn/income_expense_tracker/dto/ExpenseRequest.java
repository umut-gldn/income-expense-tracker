package com.umutgldn.income_expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
	private BigDecimal amount;
	private String description;
	private LocalDate date;
	private String category;

}
