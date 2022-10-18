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
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false, updatable = false)
	private Long reviewId;
	@Column(nullable = false)
	private double points;
	@Column(nullable = true)
	private String comment;
	@Column(nullable = false)
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("reviews")
	@JoinColumn(name = "foodId")
	private Food food;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("reviews")
	@JoinColumn(name = "userId")
	private User user;
	
	public Review() {}

	public Review(double points, String comment, String status, Food food, User user) {
		this.points = points;
		this.comment = comment;
		this.status = status;
		this.food = food;
		this.user = user;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", points=" + points + ", comment=" + comment + ", status=" + status
				+ ", food=" + food + ", user=" + user + "]";
	}
	
	
	
	

}
