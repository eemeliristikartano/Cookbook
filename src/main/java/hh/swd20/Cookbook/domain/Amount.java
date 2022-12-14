package hh.swd20.Cookbook.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Amount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long amountId;
	private String amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("amounts")
	@JoinColumn(name = "unitId")
	private Unit unit;

	@OneToMany( mappedBy = "amount")
	private List<Ingredient> ingredients;
	
	public Amount() {}

	public Amount(String amount, Unit unit) {
		super();
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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
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
		return "Amount [amountId=" + amountId + ", amount=" + amount + ", unit=" + unit + ", ingredients=" + ingredients
				+ "]";
	}
	
}
