package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import hh.swd20.Cookbook.domain.Food.Status;

public interface FoodRepository extends CrudRepository<Food, Long> {
	
	List<Food> findAllByStatus(Status status);
	
	List<Food> findAllByUser(User user);
	
	@Transactional
	@Modifying
	@Query("UPDATE Food SET status=?1 WHERE foodId=?2")
	void approveRecipe(Status status ,Long foodId);	
}
