package ptithcm.entity;

import java.util.Collection;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "nhatro")
public class NhaTro {
	@Id @GeneratedValue
	private int id;
	
	@ManyToOne @JoinColumn(name = "idchutro")
	private ChuTro chuTro;
	
	private String tieuDe;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iddiachi", referencedColumnName = "id")
    private DiaChi diachi;
	
	private int soPhongChoThue;
	private int soNguoiTrenPhong;
	private int soPhongCoSan;
	private float dienTich;
	private int tienCoc;
	private int tienThue;
	private String moTa;
	private float diem;
	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayThem;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "nhaTro")
	private Collection<LichHen> lichHen;
	
	
	
	public Collection<LichHen> getLichHen() {
		return lichHen;
	}
	public void setLichHen(Collection<LichHen> lichHen) {
		this.lichHen = lichHen;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChuTro getChuTro() {
		return chuTro;
	}
	public void setChuTro(ChuTro chuTro) {
		this.chuTro = chuTro;
	}
	public String getTieuDe() {
		return tieuDe;
	}
	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}
	public DiaChi getDiachi() {
		return diachi;
	}
	public void setDiachi(DiaChi diachi) {
		this.diachi = diachi;
	}
	public int getSoPhongChoThue() {
		return soPhongChoThue;
	}
	public void setSoPhongChoThue(int soPhongChoThue) {
		this.soPhongChoThue = soPhongChoThue;
	}
	public int getSoNguoiTrenPhong() {
		return soNguoiTrenPhong;
	}
	public void setSoNguoiTrenPhong(int soNguoiTrenPhong) {
		this.soNguoiTrenPhong = soNguoiTrenPhong;
	}
	public int getSoPhongCoSan() {
		return soPhongCoSan;
	}
	public void setSoPhongCoSan(int soPhongCoSan) {
		this.soPhongCoSan = soPhongCoSan;
	}
	public float getDienTich() {
		return dienTich;
	}
	public void setDienTich(float dienTich) {
		this.dienTich = dienTich;
	}
	public int getTienCoc() {
		return tienCoc;
	}
	public void setTienCoc(int tienCoc) {
		this.tienCoc = tienCoc;
	}
	public int getTienThue() {
		return tienThue;
	}
	public void setTienThue(int tienThue) {
		this.tienThue = tienThue;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public float getDiem() {
		return diem;
	}
	public void setDiem(float diem) {
		this.diem = diem;
	}
	public Date getNgayThem() {
		return ngayThem;
	}
	public void setNgayThem(Date ngayThem) {
		this.ngayThem = ngayThem;
	}

	
	
}
