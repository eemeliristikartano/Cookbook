package hh.swd20.Cookbook.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UnitRepository extends CrudRepository<Unit, Long> {
	
	@Query("SELECT unit FROM Unit")
	List<String> findAllUnits();
	
	Unit findByUnit(String unit);

}
