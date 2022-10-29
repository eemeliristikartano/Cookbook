package hh.swd20.Cookbook.web;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String getRecipe(@PathVariable("id")Long foodId, Model model) {
		model.addAttribute("food", frepository.findById(foodId).get());
		model.addAttribute("ingredients", frepository.findById(foodId).get().getIngredients());
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
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		//Creates jsonobject from string.
		JsonObject jsonFood = JsonParser.parseString(foodFromRest).getAsJsonObject();
		Food food = new Food();
		//:TODO when security is enabled add real user.
		//Using setters to set values for food.
		food.setUser(null);
		food.setName(jsonFood.get("name").getAsString());
		food.setInstructions(jsonFood.get("instructions").getAsString());
		food.setDateCreated(LocalDate.now());
		//Set status to "I" so admin can check it before it is public to users.
		food.setStatus("I");
		food.setCategory(crepository.findByName(jsonFood.get("category").getAsString()));
		food.setSource(jsonFood.get("source").getAsString());
		
		/*
		 * Ingredients comes in an array. In this loop it loops through array
		 * and uses setters to set values for ingredients and amounts.
		 */
		
		for (int i = 0; i < jsonFood.get("ingredients").getAsJsonArray().size(); i++) {
			Ingredient ingredient = new Ingredient();
			Amount amount = new Amount();
			//Gets ingredient from array
			String ingredientFromJson = jsonFood.get("ingredients").getAsJsonArray().get(i).getAsJsonObject().get("ingredient").getAsString();
			//Gets amount from array
			String amountFromJson = jsonFood.get("ingredients").getAsJsonArray().get(i).getAsJsonObject().get("amount").getAsString();
			//Gets unit from array
			String unitFromJson = jsonFood.get("ingredients").getAsJsonArray().get(i).getAsJsonObject().get("unit").getAsString();
			
			 // Set amount and unit for the amount
			amount.setAmount(amountFromJson);
			amount.setUnit(unitFromJson);
			arepository.save(amount);
			
			//Set name for the ingredient.
			ingredient.setName(ingredientFromJson);
			//Set amount for ingredient
			ingredient.setAmount(amount);
			//Saves the ingredient.
			irepository.save(ingredient);
			
			//Add the ingredient to list of ingredients.
			ingredients.add(ingredient);
			
			//Save food so the food can be set to ingredient. Finally save ingredient.
			frepository.save(food);
			ingredient.setFood(food);
			irepository.save(ingredient);
		}		
		return "redirect:/";
	}
	
	
	
	@GetMapping("/deleterecipe/{id}")
	public String deleteRecipe(@PathVariable("id") Long foodId) {
		frepository.deleteById(foodId);
		return "redirect:/";
	}
	
	/*
	 * Method for editing recipe. In this user can edit recipes name, instructions and source.
	 * Updaterecipe thymeleaf template provides table that has all the ingredients for the food.
	 * In that table there is a button for editing ingredient.
	 */
	
	@GetMapping("/editrecipe/{id}")
	public String updateRecipe(@PathVariable("id") Long foodId, Model model) {
		model.addAttribute("food", frepository.findById(foodId).get());
		model.addAttribute("categories", crepository.findAll());
		model.addAttribute("ingredients", frepository.findById(foodId).get().getIngredients());
		return "updaterecipe";
	}
	
	/*
	 * Commits recipes update. Sets ingredients for the food and sets
	 * date for dateEdited.
	 */
	
	@PostMapping("/updaterecipe")
	public String updateRecipe(@ModelAttribute Food food) {
		food.setIngredients(frepository.findById(food.getFoodId()).get().getIngredients());
		food.setDateEdited(LocalDate.now());
		food.setStatus("I");
		frepository.save(food);
		return "redirect:/";
	}
	
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
	
	@GetMapping("/addingredient")
	public String addIngredient() {
		//TODO: method for adding ingredient to food.
		return "newingredient";
	}
	
}
