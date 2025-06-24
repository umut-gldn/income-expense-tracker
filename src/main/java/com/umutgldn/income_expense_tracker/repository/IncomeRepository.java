package com.umutgldn.income_expense_tracker.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.umutgldn.income_expense_tracker.model.Income;
import com.umutgldn.income_expense_tracker.model.User;

@Repository
public interface IncomeRepository  extends JpaRepository<Income, Long>{
	List<Income> findByUser(User user);
	
	@Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE i.user = :user")
	BigDecimal sumAmountByUser(User user);
}
