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

import com.umutgldn.income_expense_tracker.dto.IncomeRequest;
import com.umutgldn.income_expense_tracker.dto.IncomeResponse;
import com.umutgldn.income_expense_tracker.dto.TotalResponse;
import com.umutgldn.income_expense_tracker.exception.UserNotFoundException;
import com.umutgldn.income_expense_tracker.model.User;
import com.umutgldn.income_expense_tracker.repository.UserRepository;
import com.umutgldn.income_expense_tracker.service.IncomeService;
import com.umutgldn.income_expense_tracker.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {
	
	private final IncomeService incomeService;
	private final ReportService reportService;
	private final UserRepository userRepository;
	
	private String getUsername(Principal principal) {
		return principal.getName();
	}

	
	@PostMapping
	public ResponseEntity<IncomeResponse> addIncome(@RequestBody IncomeRequest request, Principal principal){
		String username=getUsername(principal);
		IncomeResponse response=incomeService.addIncome(username, request);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<IncomeResponse>> getIncomes(Principal principal){
		String username=getUsername(principal);
		List<IncomeResponse> incomes=incomeService.getIncomes(username);
		return ResponseEntity.ok(incomes);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncome(@PathVariable Long id, Principal principal){
		String username=getUsername(principal);
		incomeService.deleteIncome(username, id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Long id, @RequestBody IncomeRequest request, Principal principal){
		String username=getUsername(principal);
		IncomeResponse updated=incomeService.updateIncome(username, id, request);
		return ResponseEntity.ok(updated);
	}
	
	@GetMapping("/total")
	public ResponseEntity<TotalResponse> getTotal(Principal principal){
		User user=userRepository.findByUsername(principal.getName())
				.orElseThrow(()->new UserNotFoundException("User not found: "+principal.getName()));
		
		TotalResponse totalResponse=reportService.getTotalByUser(user);
		return ResponseEntity.ok(totalResponse);
		
	}
	
}
