package hh.swd20.Cookbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long ingredientId;
	@Column(nullable = false)
	private String name;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ingredient")
//	private List<Amount> amounts;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("ingredients")
	@JoinColumn(name = "amountId")
	private Amount amount;
	
//	@ManyToMany(mappedBy = "ingredients")
//	private Set<Food> foods = new HashSet<Food>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("ingredients")
	@JoinColumn(name = "foodId")
	private Food food;
	
	public Ingredient() {}

	public Ingredient(String name, Amount amount, Food food) {
		super();
		this.name = name;
		this.amount = amount;
		this.food = food;
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

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	@Override
	public String toString() {
		return "Ingredient [ingredientId=" + ingredientId + ", name=" + name + ", amount=" + amount + ", food=" + food
				+ "]";
	}

		
}
