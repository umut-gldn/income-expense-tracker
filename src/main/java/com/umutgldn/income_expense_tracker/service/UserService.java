package com.umutgldn.income_expense_tracker.service;

import com.umutgldn.income_expense_tracker.dto.UserLoginRequest;
import com.umutgldn.income_expense_tracker.dto.UserRegisterRequest;
import com.umutgldn.income_expense_tracker.dto.UserResponse;

public interface UserService {
	
	UserResponse register(UserRegisterRequest request);
	
	String  login(UserLoginRequest request);

}
