package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {
	
	List<Food> findAllByStatus(String status);
	
	List<Food> findAllByUser(User user);
	
}
