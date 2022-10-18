package hh.swd20.Cookbook.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Food {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long foodId;
	@Column(nullable = false)
	private String name;
	private String instructions;
	private LocalDate dateCreated;
	private LocalDate dateEdited;
	private String status;
	private String source;
	
	@OneToMany(mappedBy = "food")
	private List<Review> reviews;

	@ManyToOne
	@JsonIgnoreProperties("foods")
	@JoinColumn(name="categoryId")
	private Category category;
	
	@ManyToOne
	@JsonIgnoreProperties("foods")
	@JoinColumn(name = "userId")
	private User user;
	
	public Food() {}

	public Food(String name, String instructions, LocalDate dateCreated, LocalDate dateEdited, String status,
			String source, Category category, User user) {
		super();
		this.name = name;
		this.instructions = instructions;
		this.dateCreated = dateCreated;
		this.dateEdited = dateEdited;
		this.status = status;
		this.source = source;
		this.category = category;
		this.user = user;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getDateEdited() {
		return dateEdited;
	}

	public void setDateEdited(LocalDate dateEdited) {
		this.dateEdited = dateEdited;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Food [foodId=" + foodId + ", name=" + name + ", instructions=" + instructions + ", dateCreated="
				+ dateCreated + ", dateEdited=" + dateEdited + ", status=" + status + ", source=" + source
				+ ", category=" + category + ", user=" + user + "]";
	}
	
	
	
	

}
