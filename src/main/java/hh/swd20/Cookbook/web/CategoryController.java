package hh.swd20.Cookbook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.Cookbook.domain.Category;
import hh.swd20.Cookbook.domain.CategoryRepository;

/*
 * Restcontroller for categories. 
 */

@CrossOrigin
@RestController
public class CategoryController {
	
	@Autowired
	private CategoryRepository crepository;
	
	//Returns a list of all the categories from database. List is used in the newfoodform so user can set category for food.
	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		return (List<Category>) crepository.findAll();
	}
	

}
