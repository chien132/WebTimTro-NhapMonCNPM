package ptithcm.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Bill")
public class Bill {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "done")
	private boolean status;
	private boolean paid;

	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	private Date buydate;

	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;

	@OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
	private Collection<BillItem> billItems;

	public int getcartqty() {
		int size = 0;
		for (BillItem i : billItems) {
			size += i.getAmount();
		}
		return size;
	}

	public long getcartvalue() {
		long value = 0;
		for (BillItem i : billItems) {
			value += (long) i.getvalue();
		}
//		System.out.println(value);
		return value;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getBuydate() {
		return buydate;
	}

	public void setBuydate(Date buydate) {
		this.buydate = buydate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(Collection<BillItem> billItems) {
		this.billItems = billItems;
	}

}
