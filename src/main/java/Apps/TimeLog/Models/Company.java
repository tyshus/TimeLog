package Apps.TimeLog.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "companies")
@Table(name = "companies")
public class Company {
	@Id
	private String id;
	private String name;
	private String address;
	private String code;
	private String vatRegNo;
	private Double rate;
	

	public Company(String id, String name, String address, String code, String vatRegNo, double rate) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.code = code;
		this.vatRegNo = vatRegNo;
		this.rate = rate;
	}


	public double getRate() {
		return rate;
	}


	public void setRate(Double rate) {
		this.rate = rate;
	}


	public Company() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getVatRegNo() {
		return vatRegNo;
	}


	public void setVatRegNo(String vatRegNo) {
		this.vatRegNo = vatRegNo;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address + ", code=" + code + ", vatRegNo="
				+ vatRegNo + ", rate=" + rate + "]";
	}

}
