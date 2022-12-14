package hh.swd20.Cookbook.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.Cookbook.domain.FoodRepository;
import hh.swd20.Cookbook.domain.SignupForm;
import hh.swd20.Cookbook.domain.User;
import hh.swd20.Cookbook.domain.UserRepository;

/*
 * Controller for signing up, logging in, savign a new user
 * and for users page.
 */

@Controller
public class UserController {
	
	@Autowired
	private UserRepository urepository;
	@Autowired
	private FoodRepository frepository;
	
	
	//Endpoint for logging in.
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	//Endpoint for signing up.
	@GetMapping("/signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
	//Validates data and saves a new user if the data is valid.
	@PostMapping("/saveuser")
	public String saveUser(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
				String password = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPassword = bc.encode(password);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPassword);
				newUser.setUsername(signupForm.getUsername());
				newUser.setRole("USER");
				if (urepository.findByUsername(signupForm.getUsername()) == null) {
					urepository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "err.username", "K??ytt??j??nimi on jo k??yt??ss??.");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Salasanat eiv??t t??sm????.");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "redirect:/login";
	}
	
	//Page where user can view users own recipes, edit them and delete them.
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('USER')")
	public String userPage(Model model, Principal p) {
		try {
			User user = urepository.findByUsername(p.getName());
			model.addAttribute("foods", frepository.findAllByUser(user));
			return "userpage";
		} catch (NullPointerException e) {
			return "somethingwentwrong";
		} catch (Exception e) {
			return "somethingwentwrong";
		}
	}
	
	
	
	
}
