package hh.swd20.Cookbook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.Cookbook.domain.Category;
import hh.swd20.Cookbook.domain.CategoryRepository;

@CrossOrigin
@RestController
public class CategoryController {
	
	@Autowired
	private CategoryRepository crepository;
	
	@GetMapping("/categories")
	public @ResponseBody List<Category> getAllCategories() {
		return (List<Category>) crepository.findAll();
	}
	

}
