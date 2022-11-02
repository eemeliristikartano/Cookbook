package hh.swd20.Cookbook.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Unit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long unitId;
	private String unit;
	
	@OneToMany(mappedBy = "unit")
	private List<Amount> amounts;
	
	public Unit() {}

	public Unit(String unit) {
		super();
		this.unit = unit;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Amount> getAmounts() {
		return amounts;
	}

	public void setAmounts(List<Amount> amounts) {
		this.amounts = amounts;
	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", unit=" + unit;
	}	

}
