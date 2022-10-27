package hh.swd20.Cookbook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.Cookbook.domain.CategoryRepository;

@CrossOrigin
@Controller
public class CategoryController {
	
	@Autowired
	private CategoryRepository crepository;
	
	@GetMapping("/categories")
	public @ResponseBody List<String> getAllCategories() {
		return (List<String>) crepository.findAllCategories();
	}
	

}
