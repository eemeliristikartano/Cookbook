package hh.swd20.Cookbook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.Cookbook.domain.Unit;
import hh.swd20.Cookbook.domain.UnitRepository;

/*
 * Restcontroller for units. 
 */

@CrossOrigin
@RestController
public class UnitController {
	
	@Autowired
	private UnitRepository unrepository;
	
	//Returns a list of all the units from database. List is used in the newfoodform so user can set unit for ingredient.
	@GetMapping("/units")
	public List<Unit> unitsRest() {
		return (List<Unit>) unrepository.findAll();
	
	}
}
