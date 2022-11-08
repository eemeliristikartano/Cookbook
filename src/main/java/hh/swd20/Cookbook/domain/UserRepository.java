package hh.swd20.Cookbook.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	//Used in many class.
	User findByUsername(String username);

}
