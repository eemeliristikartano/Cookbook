package hh.swd20.Cookbook.web;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.Cookbook.domain.Amount;
import hh.swd20.Cookbook.domain.AmountRepository;
import hh.swd20.Cookbook.domain.CategoryRepository;
import hh.swd20.Cookbook.domain.Food;
import hh.swd20.Cookbook.domain.FoodRepository;
import hh.swd20.Cookbook.domain.Ingredient;
import hh.swd20.Cookbook.domain.IngredientRepository;

@Controller
public class FoodController {
	
	@Autowired
	private FoodRepository frepository;
	@Autowired
	private IngredientRepository irepository;
	@Autowired
	private AmountRepository arepository;
	@Autowired
	private CategoryRepository crepository;
	
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
	public String getRecipe(@PathVariable("id")Long id, Model model) {
		model.addAttribute("food", frepository.findById(id).get());
		return "recipe";
	}
	
	@GetMapping("/addrecipe")
	public String addNewFood() {
		return "newfoodform";
	}
	
	/*
	 * Gets JSON as a string from frontend and saves new food.
	 */
	
	@CrossOrigin
	@PostMapping("/saverecipe")
	public @ResponseBody String saveNewFood(@RequestBody String foodFromRest) {
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		//Creates jsonobject from string.
		JsonObject jsonFood = JsonParser.parseString(foodFromRest).getAsJsonObject();
		Food food = new Food();
		//:TODO when security is enabled add real user.
		food.setUser(null);
		food.setName(jsonFood.get("name").getAsString());
		food.setInstructions(jsonFood.get("instructions").getAsString());
		food.setDateCreated(LocalDate.now());
		//Set status to "I" so admin can check it before it is public.
		food.setStatus("I");
		System.out.println(crepository.findByName(jsonFood.get("category").getAsString()));
		food.setCategory(crepository.findByName(jsonFood.get("category").getAsString()));
		food.setSource(jsonFood.get("source").getAsString());
		
		/*
		 * Ingredients comes in array. In this loop it loops through array
		 * and uses setters to set values for ingredients and amounts.
		 */
		
		for (int i = 0; i < jsonFood.get("ingredients").getAsJsonArray().size(); i++) {
			Amount amount = new Amount();
			Ingredient ingredient = new Ingredient();
			//Gets ingredient from array
			String ingredientFromJson = jsonFood.get("ingredients").getAsJsonArray().get(i).getAsJsonObject().get("ingredient").getAsString();
			//Gets amount from array
			String amountFromJson = jsonFood.get("ingredients").getAsJsonArray().get(i).getAsJsonObject().get("amount").getAsString();
			//Gets unit from array
			String unitFromJson = jsonFood.get("ingredients").getAsJsonArray().get(i).getAsJsonObject().get("unit").getAsString();
			
			//Set name for the ingredient.
			ingredient.setName(ingredientFromJson);
			//Saves the ingredient.
			irepository.save(ingredient);
			
			/*
			 * Set amount and unit for the amount and set ingredient
			 * for the amount. After that it saves the amount.
			 */
			amount.setAmount(amountFromJson);
			amount.setUnit(unitFromJson);
			amount.setIngredient(ingredient);
			arepository.save(amount);
			//Add the ready ingredient to Set of ingredients.
			ingredients.add(ingredient);
	
		}
		//Add all the ingredients to Food-object.
		food.setIngredients(ingredients);
		frepository.save(food);
		return "redirect:/";
	}
	
	@GetMapping("/deleterecipe/{id}")
	public String deleteRecipe(@PathVariable("id") Long recipeId) {
		frepository.deleteById(recipeId);
		return "redirect:/";
	}
	
	@CrossOrigin
	@GetMapping("/editrecipe/{id}")
	public @ResponseBody String updateRecipe(@PathVariable("id") Long recipeId, Model model) {
		//TODO
		return "";
	}

}
