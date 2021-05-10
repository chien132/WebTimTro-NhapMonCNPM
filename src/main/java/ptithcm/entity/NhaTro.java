package ptithcm.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	private BigDecimal tienCoc;
	private BigDecimal tienThue;
	private String moTa;
	private float diem;
	private int tinhtrang;
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
	
	public BigDecimal getTienCoc() {
		return tienCoc;
	}
	public void setTienCoc(BigDecimal tienCoc) {
		this.tienCoc = tienCoc;
	}
	public BigDecimal getTienThue() {
		return tienThue;
	}
	public void setTienThue(BigDecimal tienThue) {
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
	public int getTinhtrang() {
		return tinhtrang;
	}
	public void setTinhtrang(int tinhtrang) {
		this.tinhtrang = tinhtrang;
	}
	public int getSoLuot() {
		int soLuot = 0;
		for (LichHen lan:this.getLichHen()) {
			if(lan.getDongy()) soLuot++;
		}
		return soLuot;
	}
	public String getProvinceName() {
		return this.getDiachi().getWard().getDistrict().getProvince().getName();
	}
	public String getDistrictName() {
		return this.getDiachi().getWard().getDistrict().getName();
	}
	public String getWardName() {
		return this.getDiachi().getWard().getName();
	}
	public int getProvinceId() {
		return this.getDiachi().getWard().getDistrict().getProvince().getId();
	}
	public int getDistrictId() {
		return this.getDiachi().getWard().getDistrict().getId();
	}
	public int getWardId() {
		return this.getDiachi().getWard().getId();
	}
}
