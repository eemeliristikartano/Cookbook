package hh.swd20.Cookbook.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.Cookbook.domain.AmountRepository;
import hh.swd20.Cookbook.domain.Food;
import hh.swd20.Cookbook.domain.FoodRepository;
import hh.swd20.Cookbook.domain.Ingredient;
import hh.swd20.Cookbook.domain.IngredientRepository;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientRepository irepository;
	
	@Autowired
	private FoodRepository frepository;
	
	@Autowired
	private AmountRepository arepository;
	
	/*
	 * Method for editing ingredient in a food. 
	 */
	
	@GetMapping("/editingredient/{id}")
	public String updateIngredient(@PathVariable("id") Long ingredientId, Model model) {
		model.addAttribute("ingredient", irepository.findById(ingredientId).get());
		model.addAttribute("units", arepository.findAllUnits());
		return "updateingredient";
	}
	
	/*
	 * Commits ingredient update. Sets amount for ingredient and saves ingredient.
	 */
	
	@PostMapping("/updateingredient")
	public String updateIngredient(@ModelAttribute Ingredient ingredient) {
		Long foodId = ingredient.getFood().getFoodId();
		arepository.save(ingredient.getAmount());
		irepository.save(ingredient);
		return "redirect:/editrecipe/" + foodId;
	}
	
	/*
	 * Method for adding new ingredient to recipe.
	 */
	
	@GetMapping("/addingredient/{foodId}")
	public String addIngredient(@PathVariable("foodId") Long foodId, Model model) {
		model.addAttribute("food", frepository.findById(foodId).get());
		model.addAttribute("ingredient", new Ingredient());
		model.addAttribute("units", arepository.findAllUnits());
		return "newingredient";
	}
	
	/*
	 * Methdod that saves the new ingredient. Sets status and dateEdited on Food. 
	 */
	
	@PostMapping("/saveingredient/{foodId}")
	public String saveIngredient(@ModelAttribute Ingredient ingredient, @PathVariable("foodId") Long foodId) {
		Food food = frepository.findById(foodId).get();
		food.setStatus("I");
		food.setDateEdited(LocalDate.now());
		frepository.save(food);
		arepository.save(ingredient.getAmount());
		ingredient.setFood(food);
		irepository.save(ingredient);	
		return "redirect:/editrecipe/" + food.getFoodId();
	}

}
