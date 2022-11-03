package hh.swd20.Cookbook.domain;

import org.springframework.data.repository.CrudRepository;

public interface UnitRepository extends CrudRepository<Unit, Long> {
	
	Unit findByUnit(String unit);

}
