package com.umutgldn.income_expense_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umutgldn.income_expense_tracker.dto.AuthenticationResponse;
import com.umutgldn.income_expense_tracker.dto.UserLoginRequest;
import com.umutgldn.income_expense_tracker.dto.UserRegisterRequest;
import com.umutgldn.income_expense_tracker.dto.UserResponse;
import com.umutgldn.income_expense_tracker.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegisterRequest request){
		
		UserResponse registeredUser=userService.register(request);
		return ResponseEntity.ok(registeredUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid UserLoginRequest request)
	{
		String token=userService.login(request);
		return ResponseEntity.ok(new AuthenticationResponse(token));
		
	}

}
