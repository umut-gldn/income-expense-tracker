package com.umutgldn.income_expense_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
	 	@NotBlank(message = "First name must not be empty")
	    private String firstName;

	    @NotBlank(message = "Last name must not be empty")
	    private String lastName;

	    @NotBlank(message = "Username must not be empty")
	    private String username;

	    @NotBlank(message = "Password must not be empty")
	    @Size(min = 6, message = "Password must be at least 6 characters")
	    private String password;
}
