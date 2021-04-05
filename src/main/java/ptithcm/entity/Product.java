package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int price;
	private String des;
	private String image;
	private int discount;
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "brandid")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "cate_id")
	private Category category;

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Collection<BillItem> billItems;
	
	@OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
	private Collection<Comment> comments;

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public int getboughtqty() {
		int size = 0;
		for (BillItem i : billItems) {
			size += i.getAmount();
		}
		return size;
	}

	public long getrealprice() {
		return (long) price * (long) (100 - discount) / 100;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Collection<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(Collection<BillItem> billItems) {
		this.billItems = billItems;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

}
