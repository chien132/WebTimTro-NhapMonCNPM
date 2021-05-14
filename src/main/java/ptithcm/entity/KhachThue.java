package ptithcm.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "khachthue")
public class KhachThue implements Comparable<KhachThue>{
	@Id @GeneratedValue
	private int id;
	@ManyToOne @JoinColumn(name = "idtruong")
	private Truong truong;
	private int namSinh;
	private boolean gioiTinh;
	private String queQuan;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkusername", referencedColumnName = "username")
    private Account account;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "khachThue")
	private Collection<LichHen> lichHen;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "khachThue")
	private Collection<Comment> comments;
	
	

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Truong getTruong() {
		return truong;
	}

	public void setTruong(Truong truong) {
		this.truong = truong;
	}

	public int getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(int namSinh) {
		this.namSinh = namSinh;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getQueQuan() {
		return queQuan;
	}

	public void setQueQuan(String queQuan) {
		this.queQuan = queQuan;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Collection<LichHen> getLichHen() {
		return lichHen;
	}

	public void setLichHen(Collection<LichHen> lichHen) {
		this.lichHen = lichHen;
	}

	@Override
	public int compareTo(KhachThue o) {
		// TODO Auto-generated method stub
		int diem = 0;
		if(o.getNamSinh()-2 < this.getNamSinh() && this.getNamSinh() < o.getNamSinh()+2) diem++;
		if(o.getTruong()==this.getTruong()) diem++;
		if(o.getQueQuan()==this.getQueQuan()) diem++;
		return diem;
	}
}
