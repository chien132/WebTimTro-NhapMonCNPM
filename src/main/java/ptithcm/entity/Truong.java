package ptithcm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity @Table(name = "truong")
public class Truong {
	@Id @GeneratedValue
	private int id;
	private String ten;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iddiachi", referencedColumnName = "id")
    private DiaChi diachi;

}
