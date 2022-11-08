package hh.swd20.Cookbook.domain;

import org.springframework.data.repository.CrudRepository;

public interface UnitRepository extends CrudRepository<Unit, Long> {
	//Used in FoodController to add unit to ingredient.
	Unit findByUnit(String unit);

}
