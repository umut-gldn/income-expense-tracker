package com.umutgldn.income_expense_tracker.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.umutgldn.income_expense_tracker.model.Expense;
import com.umutgldn.income_expense_tracker.model.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	
	List<Expense> findByUser(User user);
	
	@Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user")
	BigDecimal sumAmountByUser(User user);

}
