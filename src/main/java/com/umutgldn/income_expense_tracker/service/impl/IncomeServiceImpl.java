package com.umutgldn.income_expense_tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umutgldn.income_expense_tracker.dto.IncomeRequest;
import com.umutgldn.income_expense_tracker.dto.IncomeResponse;
import com.umutgldn.income_expense_tracker.exception.IncomeNotFoundException;
import com.umutgldn.income_expense_tracker.exception.UnauthorizedException;
import com.umutgldn.income_expense_tracker.exception.UserNotFoundException;
import com.umutgldn.income_expense_tracker.mapper.IncomeMapper;
import com.umutgldn.income_expense_tracker.model.Income;
import com.umutgldn.income_expense_tracker.model.User;
import com.umutgldn.income_expense_tracker.repository.IncomeRepository;
import com.umutgldn.income_expense_tracker.repository.UserRepository;
import com.umutgldn.income_expense_tracker.service.IncomeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

	private final UserRepository userRepository;
	private final IncomeRepository incomeRepository;
	private final IncomeMapper incomeMapper;
	
	@Override
	public IncomeResponse addIncome(String username, IncomeRequest request) {
		User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found"));
		
		Income income=incomeMapper.toIncome(request);
		income.setUser(user);
		
		Income savedIncome=incomeRepository.save(income);
		return incomeMapper.toIncomeResponse(savedIncome);
	}

	@Override
	public List<IncomeResponse> getIncomes(String username) {
		User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found"));
		
		List<Income> incomes=incomeRepository.findByUser(user);	
		return incomes.stream()
				.map(incomeMapper::toIncomeResponse)
				.toList();
	}

	@Override
	public void deleteIncome(String username, Long incomeId) {
		User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found"));
		Income income=incomeRepository.findById(incomeId).orElseThrow(()->new IncomeNotFoundException("Income not found"));
		if(!income.getUser().equals(user)) {
			throw new UnauthorizedException("Unauthorized to delete this income");
		}
		incomeRepository.delete(income);
	}

	@Override
	public IncomeResponse updateIncome(String username, Long id, IncomeRequest request) {
		User user=userRepository.findByUsername(username)
				.orElseThrow(()->new UserNotFoundException("User not found"));
		Income income=incomeRepository.findById(id)
				.orElseThrow(()->new IncomeNotFoundException("Income not found"));
		if(!income.getUser().equals(user)) {
			throw new UnauthorizedException("Unauthorized to update this income");
		}
		incomeMapper.updateIncomeFromRequest(request, income);
		return incomeMapper.toIncomeResponse(incomeRepository.save(income));
	}

}
