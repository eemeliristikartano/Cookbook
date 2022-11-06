package hh.swd20.Cookbook.web;


import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import hh.swd20.Cookbook.domain.Amount;
import hh.swd20.Cookbook.domain.AmountRepository;
import hh.swd20.Cookbook.domain.CategoryRepository;
import hh.swd20.Cookbook.domain.Food;
import hh.swd20.Cookbook.domain.Food.Status;
import hh.swd20.Cookbook.domain.FoodRepository;
import hh.swd20.Cookbook.domain.Ingredient;
import hh.swd20.Cookbook.domain.IngredientRepository;
import hh.swd20.Cookbook.domain.UnitRepository;
import hh.swd20.Cookbook.domain.User;
import hh.swd20.Cookbook.domain.UserRepository;

/*
 * This controller contains methods for creating, reading, updating and deleting recipes.
 */


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
	@Autowired
	private UserRepository urepository;
	@Autowired
	private UnitRepository unrepository;
	
	/*
	 *Index endpoint contains list of recipes from DB. It is visible to everyone.
	 */
	@GetMapping("/")
	public String index(Model model) {
		//Only recipes that have been accepted by the admin.
		model.addAttribute("foods", frepository.findAllByStatus(Status.APPROVED));
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
	@PreAuthorize("hasAuthority('USER')")
	public String addNewFood() {
		return "newfoodform";
	}
	
	/*
	 * Gets JSON as a string from frontend and saves new food.
	 */
	
	@CrossOrigin
	@PostMapping("/saverecipe")
	@PreAuthorize("hasAuthority('USER')")
	public @ResponseBody String saveNewFood(@RequestBody String foodFromRest, Principal p) {
		try {
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			//Creates jsonobject from string.
			JsonObject jsonFood = JsonParser.parseString(foodFromRest).getAsJsonObject();
			System.out.println(jsonFood);
			Food food = new Food();
			//Using setters to set values for food.
			food.setUser(urepository.findByUsername(p.getName()));
			food.setName(jsonFood.get("name").getAsString());
			food.setInstructions(jsonFood.get("instructions").getAsString());
			food.setDateCreated(LocalDate.now());
			//Set status to "I" so admin can check it before it is public to users.
			food.setStatus(Status.REVIEW);
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
				amount.setUnit(unrepository.findByUnit(unitFromJson));
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

			return "redirect:/user";
			
		} catch (JsonSyntaxException e) {
			return "somethingwentwrong";
		} catch (NullPointerException e) {
			return "somethingwentwrong";
		} catch (Exception e) {
			return "somethingwentwrong";
		}
	}
	
	
	
	@GetMapping("/deleterecipe/{foodId}")
	@PreAuthorize("hasAuthority('USER')")
	public String deleteRecipe(@PathVariable("foodId") Long foodId, Principal p) {
		try {
			User user = urepository.findByUsername(p.getName());
			Food food = frepository.findById(foodId).get();
			if (food.getUser().getUserId().equals(user.getUserId())) {
				frepository.deleteById(foodId);
				return "redirect:/user";
			}
			return "notallowed";
		} catch (NullPointerException e) {
			return "somethingwentwrong";
		}
	}
	
	/*
	 * Method for editing recipe. In this user can edit recipes name, instructions and source.
	 * Updaterecipe thymeleaf template provides table that has all the ingredients for the food.
	 * In that table there is a button for editing ingredient.
	 */
	
	@GetMapping("/editrecipe/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public String updateRecipe(@PathVariable("id") Long foodId, Model model, Principal p) {
		try {
			User user = urepository.findByUsername(p.getName());
			Food food = frepository.findById(foodId).get();
			if (food.getUser().getUserId().equals(user.getUserId())) {
				model.addAttribute("food", food);
				model.addAttribute("categories", crepository.findAll());
				model.addAttribute("ingredients", food.getIngredients());
				return "updaterecipe";
			} 
			return "notallowed";
		} catch (NullPointerException e) {
			return "somethingwentwrong";
		}
	}
	
	/*
	 * Commits recipes update. Sets ingredients for the food and sets
	 * date for dateEdited.
	 */
	
	@PostMapping("/updaterecipe")
	@PreAuthorize("hasAuthority('USER')")
	public String updateRecipe(@ModelAttribute Food food, Principal p) {
		try {
			User user = urepository.findByUsername(p.getName());
			if (food.getUser().getUserId().equals(user.getUserId())) {
				food.setIngredients(frepository.findById(food.getFoodId()).get().getIngredients());
				food.setDateEdited(LocalDate.now());
				food.setStatus(Status.REVIEW);
				food.setUser(user);
				frepository.save(food);
				return "redirect:/user";
			}
			return "notallowed";
		} catch (NullPointerException e) {
			return "somethingwentwrong";
		}
	}
		
	
}
