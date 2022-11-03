package hh.swd20.Cookbook.web;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.Cookbook.domain.Amount;
import hh.swd20.Cookbook.domain.AmountRepository;
import hh.swd20.Cookbook.domain.Food;
import hh.swd20.Cookbook.domain.Food.Status;
import hh.swd20.Cookbook.domain.FoodRepository;
import hh.swd20.Cookbook.domain.Ingredient;
import hh.swd20.Cookbook.domain.IngredientRepository;
import hh.swd20.Cookbook.domain.Unit;
import hh.swd20.Cookbook.domain.UnitRepository;
import hh.swd20.Cookbook.domain.User;
import hh.swd20.Cookbook.domain.UserRepository;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientRepository irepository;
	
	@Autowired
	private FoodRepository frepository;
	
	@Autowired
	private AmountRepository arepository;
	
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private UnitRepository unrepository;
	
	/*
	 * Method for editing ingredient in a food. 
	 */
	
	@GetMapping("/editingredient/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public String updateIngredient(@PathVariable("id") Long ingredientId, Model model, Principal p) {
		User user = urepository.findByUsername(p.getName());
		Food food = irepository.findById(ingredientId).get().getFood();
		if (food.getUser().getUserId().equals(user.getUserId())) {
			model.addAttribute("ingredient", irepository.findById(ingredientId).get());
			model.addAttribute("units", unrepository.findAll());
			return "updateingredient";
		}
		return "notallowed";
	}
	
	/*
	 * Commits ingredient update. Sets amount for ingredient and saves ingredient.
	 */
	
	@PostMapping("/updateingredient")
	@PreAuthorize("hasAuthority('USER')")
	public String updateIngredient(@ModelAttribute Ingredient ingredient, Principal p) {
		User user = urepository.findByUsername(p.getName());
		Food food = frepository.findById(ingredient.getFood().getFoodId()).get();
		Unit unit = ingredient.getAmount().getUnit();
		Amount amount = ingredient.getAmount();
		System.out.println(amount);
		if (food.getUser().getUserId().equals(user.getUserId())) {
			amount.setUnit(unit);
			arepository.save(amount);
			irepository.save(ingredient);
			return "redirect:/editrecipe/" + food.getFoodId();
		}
		return "notallowed";
	}
	
	/*
	 * Method for adding new ingredient to recipe.
	 */
	
	@GetMapping("/addingredient/{foodId}")
	@PreAuthorize("hasAuthority('USER')")
	public String addIngredient(@PathVariable("foodId") Long foodId, Model model, Principal p) {
		User user = urepository.findByUsername(p.getName());
		Food food = frepository.findById(foodId).get();
		if (food.getUser().getUserId().equals(user.getUserId())) {
			model.addAttribute("food", food);
			model.addAttribute("ingredient", new Ingredient());
			model.addAttribute("units", unrepository.findAll());
			return "newingredient";
		}
		return "notallowed";
	}
	
	/*
	 * Methdod that saves the new ingredient. Sets status and dateEdited on Food. 
	 */
	
	@PostMapping("/saveingredient/{foodId}")
	@PreAuthorize("hasAuthority('USER')")
	public String saveIngredient(@ModelAttribute Ingredient ingredient, @PathVariable("foodId") Long foodId, Principal p) {
		User user = urepository.findByUsername(p.getName());
		Food food = frepository.findById(foodId).get();
		if (food.getUser().getUserId().equals(user.getUserId())) {
			food.setStatus(Status.REVIEW);
			food.setDateEdited(LocalDate.now());
			frepository.save(food);
			arepository.save(ingredient.getAmount());
			ingredient.setFood(food);
			irepository.save(ingredient);	
			return "redirect:/editrecipe/" + food.getFoodId();
		}
		return "notallowed";
	}
	
	@GetMapping("/deleteingredient/{ingredientId}")
	@PreAuthorize("hasAuthority('USER')")
	public String deleteIngredient(@PathVariable("ingredientId") Long ingredientId, Principal p) {
		Ingredient ingredient = irepository.findById(ingredientId).get();
		User user = urepository.findByUsername(p.getName());
		Food food = ingredient.getFood();
		if (food.getUser().getUserId().equals(user.getUserId())) {
			irepository.deleteById(ingredientId);
			return "redirect:/editrecipe/" + food.getFoodId();
		}
		return "notallowed";
	}

}
