package hh.swd20.Cookbook.domain;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	//Used in FoodController to add category to new recipe.
	Category findByName(String category);

}
