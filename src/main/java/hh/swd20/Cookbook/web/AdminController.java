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
import hh.swd20.Cookbook.domain.User;
import hh.swd20.Cookbook.domain.UserRepository;

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
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminPage() {
			return "adminpage";
	}
	
	@GetMapping("/allrecipes")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String recipes(Model model) {
			model.addAttribute("foods", frepository.findAll());
			return "allrecipes";
	}
	
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

	
	@GetMapping("/approve/{foodId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String approveRecipe(@PathVariable("foodId") Long foodId) {
			frepository.approveRecipe(Status.APPROVED, foodId);
			return "redirect:/reviewrecipes";
	}
	
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String users(Model model, Principal p) {
			model.addAttribute("users", urepository.findAll());
			return "users";
	}

}
