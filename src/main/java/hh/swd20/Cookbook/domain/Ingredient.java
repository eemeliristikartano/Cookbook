package hh.swd20.Cookbook.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false, updatable = false)
	private Long ingredientId;
	@Column(nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("ingredients")
	@JoinColumn(name = "amountId")
	private Amount amount;
	
	@ManyToMany(mappedBy = "ingredients")
	private Set<Food> foods = new HashSet<Food>();
	
	public Ingredient() {}

	public Ingredient(String name, Amount amount) {
		super();
		this.name = name;
		this.amount = amount;
		
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

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Set<Food> getFoods() {
		return foods;
	}

	public void setFoods(Set<Food> foods) {
		this.foods = foods;
	}

	@Override
	public String toString() {
		return "Ingredient [ingredientId=" + ingredientId + ", name=" + name + ",]";
	}
	
	

	
	
	

	
	
	
	
}