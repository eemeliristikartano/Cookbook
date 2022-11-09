package hh.swd20.Cookbook.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import hh.swd20.Cookbook.domain.Food.Status;
import hh.swd20.Cookbook.domain.FoodRepository;
import hh.swd20.Cookbook.domain.UserRepository;

/*
 * Controller provides endpoints for admin to:
 * see all the recipes, delete recipe, delete user and review and approve recipes. 
 */


@Controller
public class AdminController {
	
	@Autowired
	private UserRepository urepository;
	@Autowired
	private FoodRepository frepository;
	
	@GetMapping("/notallowed")
	public String notAllowed() {
		return "notallowed";
	}
	
	/*
	 * Homepage for admin.
	 */
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminPage() {
			return "adminpage";
	}
	
	/*
	 * All the recipes in database. Here admin sees if a recipe is
	 * approved or under review. Here admin can delete recipe.
	 */
	
	@GetMapping("/allrecipes")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String recipes(Model model) {
			model.addAttribute("foods", frepository.findAll());
			return "allrecipes";
	}
	
	/*
	 * Provides deleting recipe.
	 */
	
	@GetMapping("/delete/{foodId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteRecipe(@PathVariable("foodId") Long foodId) {
			frepository.deleteById(foodId);
			return "redirect:/allrecipes";
	}
	
	@GetMapping("/reviewrecipes")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String reviewRecipes(Model model) {
			model.addAttribute("foods", frepository.findAllByStatus(Status.REVIEW));
			return "reviewrecipes";
	}
	
	/*
	 * Here admin sees recipes that are under review.
	 * Admin can approve or delete recipe here.
	 */
	
	@GetMapping("/approve/{foodId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String approveRecipe(@PathVariable("foodId") Long foodId) {
			frepository.approveRecipe(Status.APPROVED, foodId);
			return "redirect:/reviewrecipes";
	}
	
	/*
	 * Admin sees all users. Admin can delete users here.
	 */
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String users(Model model, Principal p) {
			model.addAttribute("users", urepository.findAll());
			return "users";
	}
	
	/*
	 * Provides user delete for admin.
	 */
	
	@GetMapping("/deleteuser/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable("userId") Long userId) {
		urepository.deleteById(userId);
		return "redirect:/users";
	}

}
