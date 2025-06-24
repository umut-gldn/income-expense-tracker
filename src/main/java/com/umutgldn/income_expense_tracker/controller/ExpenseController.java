package com.umutgldn.income_expense_tracker.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umutgldn.income_expense_tracker.dto.ExpenseRequest;
import com.umutgldn.income_expense_tracker.dto.ExpenseResponse;
import com.umutgldn.income_expense_tracker.dto.TotalResponse;
import com.umutgldn.income_expense_tracker.exception.UserNotFoundException;
import com.umutgldn.income_expense_tracker.model.User;
import com.umutgldn.income_expense_tracker.repository.UserRepository;
import com.umutgldn.income_expense_tracker.service.ExpenseService;
import com.umutgldn.income_expense_tracker.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
	
	private final ExpenseService expenseService;
	private final ReportService reportService;
	private final UserRepository userRepository;
	
	private String getUsername(Principal principal) {
		return principal.getName();
	}

	
	@PostMapping
	public ResponseEntity<ExpenseResponse> addExpense(@RequestBody ExpenseRequest request,Principal principal){
		String username=getUsername(principal);
		ExpenseResponse response=expenseService.addExpense(username, request);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ExpenseResponse>> getExpenses(Principal principal){
		String username =getUsername(principal);
		
		List<ExpenseResponse> expenses=expenseService.getExpenses(username);
		return ResponseEntity.ok(expenses);
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id,Principal principal){
		String username=getUsername(principal);
		expenseService.deleteExpense(username, id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @RequestBody ExpenseRequest request, Principal principal){
		String username=getUsername(principal);
		ExpenseResponse response=expenseService.updateExpense(id, request, username);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/total")
	public ResponseEntity<TotalResponse> getTotal(Principal principal){
		User user=userRepository.findByUsername(principal.getName())
				.orElseThrow(()->new UserNotFoundException("User not found: "+principal.getName()));
		
		TotalResponse totalResponse=reportService.getTotalByUser(user);
		return ResponseEntity.ok(totalResponse);
		
	}

}
