package hh.swd20.Cookbook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import hh.swd20.Cookbook.web.AdminController;
import hh.swd20.Cookbook.web.CategoryController;
import hh.swd20.Cookbook.web.FoodController;
import hh.swd20.Cookbook.web.IngredientController;
import hh.swd20.Cookbook.web.UnitController;
import hh.swd20.Cookbook.web.UserController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CookbookApplicationTests {
	
	@Autowired
	private AdminController acontroller;
	@Autowired
	private CategoryController ccontroller;
	@Autowired 
	private FoodController fcontroller;
	@Autowired
	private IngredientController icontroller;
	@Autowired
	private UnitController uncontroller;
	@Autowired
	private UserController uscontroller;
	
	/*
	 * Testing major functions of software.
	 */

	@Test
	void contextLoads() throws Exception {
		assertThat(acontroller).isNotNull();
		assertThat(ccontroller).isNotNull();
		assertThat(fcontroller).isNotNull();
		assertThat(icontroller).isNotNull();
		assertThat(uncontroller).isNotNull();
		assertThat(uscontroller).isNotNull();
	}

}
