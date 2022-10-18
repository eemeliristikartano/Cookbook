package hh.swd20.Cookbook.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.swd20.Cookbook.domain.FoodRepository;

@Controller
public class FoodController {
	
	@Autowired
	private FoodRepository frepository;
	
	/*
	 *Index endpoint contains list of recipes from DB. It is visible to everyone.
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("recipes", frepository.findAll());
		return "index";
	}

}
