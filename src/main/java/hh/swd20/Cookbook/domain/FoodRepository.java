package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hh.swd20.Cookbook.domain.Food.Status;

public interface FoodRepository extends CrudRepository<Food, Long> {
	
	List<Food> findAllByStatus(Status status);
	
	List<Food> findAllByUser(User user);
	
}
