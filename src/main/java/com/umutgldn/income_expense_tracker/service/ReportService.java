package com.umutgldn.income_expense_tracker.service;

import com.umutgldn.income_expense_tracker.dto.TotalResponse;
import com.umutgldn.income_expense_tracker.model.User;

public interface ReportService {
	
	   TotalResponse getTotalByUser(User user);
}
