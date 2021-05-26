package ptithcm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "idnhatro")
	private NhaTro nhaTro;

	@ManyToOne
	@JoinColumn(name = "idkhachthue")
	private KhachThue khachThue;

	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	
	private Date thoigian;
	private String comment;
	private float diem;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public NhaTro getNhaTro() {
		return nhaTro;
	}
	public void setNhaTro(NhaTro nhaTro) {
		this.nhaTro = nhaTro;
	}
	public KhachThue getKhachThue() {
		return khachThue;
	}
	public void setKhachThue(KhachThue khachThue) {
		this.khachThue = khachThue;
	}
	public Date getThoigian() {
		return thoigian;
	}
	public void setThoigian(Date thoigian) {
		this.thoigian = thoigian;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public float getDiem() {
		return diem;
	}
	public void setDiem(float diem) {
		this.diem = diem;
	}
	
	
}
