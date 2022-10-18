package hh.swd20.Cookbook.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Amount {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false, updatable = false)
	private Long amountId;
	private String amount;
	private String unit;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "amount")
	private List<Ingredient> ingredients;
	
	public Amount() {}

	public Amount(String amount, String unit) {
		this.amount = amount;
		this.unit = unit;
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

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Amount [amount=" + amount + ", unit=" + unit + "]";
	}

	
	
	

}
