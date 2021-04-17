package ptithcm.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "thongbao")
public class ThongBao {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne@JoinColumn(name = "account")
	private Account account;
	
	private Date thoigian;
	private String thongbao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Date getThoigian() {
		return thoigian;
	}
	public void setThoigian(Date thoigian) {
		this.thoigian = thoigian;
	}
	public String getThongbao() {
		return thongbao;
	}
	public void setThongbao(String thongbao) {
		this.thongbao = thongbao;
	}
	
	
}
