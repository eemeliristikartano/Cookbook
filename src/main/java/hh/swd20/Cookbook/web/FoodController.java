package hh.swd20.Cookbook.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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
		model.addAttribute("foods", frepository.findAll());
		return "index";
	}
	
	/*
	 * Finds recipe by id from DB and lists name, ingredients, instructions,
	 * date created and username who published recipe. Visible to everyone.
	 */
	
	@GetMapping("/recipe/{id}")
	public String recipe(@PathVariable("id")Long id, Model model) {
		model.addAttribute("food", frepository.findById(id).get());
		return "recipe";
	}

}
