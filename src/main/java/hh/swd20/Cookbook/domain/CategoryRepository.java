package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	@Query("SELECT DISTINCT name FROM Category")
	List<String> findAllCategories();
	
	Category findByName(String category);

}
