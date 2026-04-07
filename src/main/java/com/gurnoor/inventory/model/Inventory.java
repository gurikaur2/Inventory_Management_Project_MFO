package com.gurnoor.inventory.model;


public class Inventory {
	private Long inventoryId;
	private String itemName;
	private String category;
	private double price;
	private int quantity;
	private Long supplierId;
	private Long warehouseId;
	
	public Inventory() {
	}
	

	public Inventory(Long inventoryId, String itemName, String category, double price, int quantity, Long supplierId, Long warehouseId) {
		super();
		this.inventoryId = inventoryId;
		this.itemName = itemName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.supplierId = supplierId;
		this.warehouseId = warehouseId;
	}
	
	

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", itemName=" + itemName + ", category=" + category
				+ ", price=" + price + ", quantity=" + quantity + ", supplierId=" + supplierId + ", warehouseId="
				+ warehouseId + "]";
	}
	
	
	
	
	
}
