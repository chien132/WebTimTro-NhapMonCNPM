package ptithcm.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "chutro")
public class ChuTro {
	@Id @GeneratedValue
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkusername", referencedColumnName = "username")
    private Account account;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "chuTro")
	private Collection<NhaTro> nhaTro;

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

	public Collection<NhaTro> getNhaTro() {
		return nhaTro;
	}

	public void setNhaTro(Collection<NhaTro> nhaTro) {
		this.nhaTro = nhaTro;
	}
	
	
}
