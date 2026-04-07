package com.gurnoor.inventory.model;

public class Supplier {
	private long supplierId;
	private String supplierName;
	private String supplierEmail;
	private String supplierPhone;
	
	public Supplier(long supplierId, String supplierName, String supplierEmail, String supplierPhone) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.supplierEmail = supplierEmail;
		this.supplierPhone = supplierPhone;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", supplierEmail="
				+ supplierEmail + ", supplierPhone=" + supplierPhone + "]";
	}
	
	
	
	
}
