package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import hh.swd20.Cookbook.domain.Food.Status;

public interface FoodRepository extends CrudRepository<Food, Long> {
	//Used in FoodController to get all approved recipes that are shown to users.
	List<Food> findAllByStatus(Status status);
	//Used in UserController to show to user its own recipes.
	List<Food> findAllByUser(User user);
	//Used in AdminController to set Status from REVIEW to APPROVE by admin. 
	@Transactional
	@Modifying
	@Query("UPDATE Food SET status=?1 WHERE foodId=?2")
	void approveRecipe(Status status ,Long foodId);	
}
