package hh.swd20.Cookbook.domain;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO Amounts(ingredient_id, amount_id) VALUES(?1, ?2)", nativeQuery = true)
	void insertToAmounts(Long ingredientId, Long amountId);

}
