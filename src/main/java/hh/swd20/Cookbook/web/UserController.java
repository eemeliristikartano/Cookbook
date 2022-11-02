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

@Controller
public class UserController {
	
	@Autowired
	private UserRepository urepository;
	@Autowired
	private FoodRepository frepository;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
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
					bindingResult.rejectValue("username", "err.username", "Käyttäjänimi on jo käytössä.");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Salasanat eivät täsmää.");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('USER')")
	public String userPage(Model model, Principal p) {
		User user = urepository.findByUsername(p.getName());
		model.addAttribute("foods", frepository.findAllByUser(user));
		return "userpage";
	}
	
	
}
