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
public class Amount {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false, updatable = false)
	private Long amountId;
	private String amount;
	private String unit;
	
//	@ManyToMany(mappedBy = "amounts")
//	private Set<Ingredient> ingredients = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("amounts")
	@JoinColumn(name = "ingredientId")
	private Ingredient ingredient;
	
	
	
	public Amount() {}



	public Amount(String amount, String unit, Ingredient ingredient) {
		super();
		this.amount = amount;
		this.unit = unit;
		this.ingredient = ingredient;
	}



	public Long getAmountId() {
		return amountId;
	}



	public void setAmountId(Long amountId) {
		this.amountId = amountId;
	}



	public String getAmount() {
		return amount;
	}



	public void setAmount(String amount) {
		this.amount = amount;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public Ingredient getIngredient() {
		return ingredient;
	}



	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}



	@Override
	public String toString() {
		return "Amount [amountId=" + amountId + ", amount=" + amount + ", unit=" + unit + ", ingredient=" + ingredient
				+ "]";
	}

	
}
