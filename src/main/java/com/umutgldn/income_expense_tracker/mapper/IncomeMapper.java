package com.umutgldn.income_expense_tracker.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.umutgldn.income_expense_tracker.dto.IncomeRequest;
import com.umutgldn.income_expense_tracker.dto.IncomeResponse;
import com.umutgldn.income_expense_tracker.model.Income;

@Mapper(componentModel = "spring")
public interface IncomeMapper {
	
	IncomeResponse toIncomeResponse(Income income);

	Income toIncome(IncomeRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateIncomeFromRequest(IncomeRequest request, @MappingTarget Income income);
	
}
