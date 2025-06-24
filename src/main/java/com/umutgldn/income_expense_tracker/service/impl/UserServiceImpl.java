package com.umutgldn.income_expense_tracker.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umutgldn.income_expense_tracker.dto.UserLoginRequest;
import com.umutgldn.income_expense_tracker.dto.UserRegisterRequest;
import com.umutgldn.income_expense_tracker.dto.UserResponse;
import com.umutgldn.income_expense_tracker.exception.InvalidCredentialsException;
import com.umutgldn.income_expense_tracker.exception.UsernameAlreadyExistsException;
import com.umutgldn.income_expense_tracker.mapper.UserMapper;
import com.umutgldn.income_expense_tracker.model.User;
import com.umutgldn.income_expense_tracker.repository.UserRepository;
import com.umutgldn.income_expense_tracker.security.JwtService;
import com.umutgldn.income_expense_tracker.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl  implements UserService{
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	

	@Override
	public UserResponse register(UserRegisterRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new UsernameAlreadyExistsException("Username is already taken");
		}
		User user=userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser=userRepository.save(user);
		return userMapper.toUserResponse(savedUser);
	}

	@Override
	public String login(UserLoginRequest request) {
		User user=userRepository.findByUsername(request.getUsername()).orElseThrow(()->new RuntimeException("Invalid username or password"));
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid username or password");
		}
		return jwtService.generateToken(user);
	}

}
