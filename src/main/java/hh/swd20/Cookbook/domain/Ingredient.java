package hh.swd20.Cookbook.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false, updatable = false)
	private Long ingredientId;
	@Column(nullable = false)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ingredient")
	private List<Amount> amounts;
	
	@ManyToMany(mappedBy = "ingredients")
	private Set<Food> foods = new HashSet<Food>();
	
	public Ingredient() {}

	public Ingredient(String name, List<Amount> amounts, Set<Food> foods) {
		super();
		this.name = name;
		this.amounts = amounts;
		this.foods = foods;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Amount> getAmounts() {
		return amounts;
	}

	public void setAmounts(List<Amount> amounts) {
		this.amounts = amounts;
	}

	public Set<Food> getFoods() {
		return foods;
	}

	public void setFoods(Set<Food> foods) {
		this.foods = foods;
	}

	@Override
	public String toString() {
		return "Ingredient [ingredientId=" + ingredientId + ", name=" + name + "]";
	}

		
}
