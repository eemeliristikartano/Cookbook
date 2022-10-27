package hh.swd20.Cookbook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.Cookbook.domain.AmountRepository;

@CrossOrigin
@Controller
public class AmountController {
	
	@Autowired AmountRepository arepository;
	
	@GetMapping("/units")
	public @ResponseBody List<String> unitsRest() {
		return (List<String>) arepository.findAllUnits();
	}

}
