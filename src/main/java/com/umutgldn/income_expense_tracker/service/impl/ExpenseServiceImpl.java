package com.umutgldn.income_expense_tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umutgldn.income_expense_tracker.dto.ExpenseRequest;
import com.umutgldn.income_expense_tracker.dto.ExpenseResponse;
import com.umutgldn.income_expense_tracker.exception.ExpenseNotFoundException;
import com.umutgldn.income_expense_tracker.exception.UnauthorizedException;
import com.umutgldn.income_expense_tracker.exception.UserNotFoundException;
import com.umutgldn.income_expense_tracker.mapper.ExpenseMapper;
import com.umutgldn.income_expense_tracker.model.Expense;
import com.umutgldn.income_expense_tracker.model.User;
import com.umutgldn.income_expense_tracker.repository.ExpenseRepository;
import com.umutgldn.income_expense_tracker.repository.UserRepository;
import com.umutgldn.income_expense_tracker.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{
	
	private final ExpenseRepository expenseRepository;
	private final ExpenseMapper expenseMapper;
	private final UserRepository userRepository;
	
	
	@Override
	public ExpenseResponse addExpense(String username,ExpenseRequest request) {
		User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found"));
		
		Expense expense=expenseMapper.toExpense(request);
		expense.setUser(user);
		
		Expense savedExpense=expenseRepository.save(expense);
		return expenseMapper.toExpenseResponse(savedExpense);
	}

	@Override
	public List<ExpenseResponse> getExpenses(String username) {
		User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found"));

		List<Expense> expenses=expenseRepository.findByUser(user);
		
		return expenses.stream()
				.map(expenseMapper::toExpenseResponse)
				.toList();
	}

	@Override
	public void deleteExpense(String username, Long id) {
		
		User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found"));
		Expense expense=expenseRepository.findById(id).orElseThrow(()->new ExpenseNotFoundException("Expense not found"));
		if(!expense.getUser().equals(user)) {
			throw new UnauthorizedException("Unauthorized to delete this expense");
		}
		expenseRepository.delete(expense);
		
	}

	@Override
	public ExpenseResponse updateExpense(Long id, ExpenseRequest request, String username) {
	
		User user=userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
		Expense expense=expenseRepository.findById(id).orElseThrow(()->new RuntimeException("Expense not found"));
		if(!expense.getUser().equals(user)) {
			throw new RuntimeException("Unauthorized to update this expense");
		}
		expenseMapper.updateExpenseFromRequest(request, expense);
	
		return expenseMapper.toExpenseResponse(expenseRepository.save(expense));
	}

}
