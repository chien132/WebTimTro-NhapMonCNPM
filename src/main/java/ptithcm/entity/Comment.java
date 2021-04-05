package ptithcm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id @GeneratedValue
	private int id;
	
	@ManyToOne @JoinColumn(name = "uid")
	private User user;
	
	@ManyToOne @JoinColumn(name = "pid")
	private Product product;
	
	private boolean upvote;
	private String comment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public boolean isUpvote() {
		return upvote;
	}
	public void setUpvote(boolean upvote) {
		this.upvote = upvote;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
