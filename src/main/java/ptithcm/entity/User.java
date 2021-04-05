package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	@Id @GeneratedValue
	private int id;
	private String email;
	private String username;
	private String password;
	private String avatar;
	private boolean admin;
	private String fullname;

	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private Collection<Bill> bills;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private Collection<Comment> comments;

	
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	public int getbillsize() {
		return bills.size();
	}
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}
	
	

}
