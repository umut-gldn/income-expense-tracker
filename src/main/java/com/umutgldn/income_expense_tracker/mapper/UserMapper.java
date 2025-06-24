package com.umutgldn.income_expense_tracker.mapper;

import org.mapstruct.Mapper;

import com.umutgldn.income_expense_tracker.dto.UserRegisterRequest;
import com.umutgldn.income_expense_tracker.dto.UserResponse;
import com.umutgldn.income_expense_tracker.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	User toUser(UserRegisterRequest request);
	
	UserResponse toUserResponse(User user);

}
