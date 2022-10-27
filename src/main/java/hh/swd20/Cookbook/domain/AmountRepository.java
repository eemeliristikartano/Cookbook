package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AmountRepository extends CrudRepository<Amount, Long> {
	
	@Query("SELECT DISTINCT unit FROM Amount")
	List<String> findAllUnits();
	
	List<Amount> findByAmountAndUnit(String amount, String unit);
	
	Amount findByAmount(String amount);

}
