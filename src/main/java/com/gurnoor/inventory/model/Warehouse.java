package com.gurnoor.inventory.model;

public class Warehouse {
	private long warehouseId;
	private String warehouseName;
	private String warehouseLocation;
	private int capacity;
	
	
	public Warehouse(long warehouseId, String warehouseName, String warehouseLocation, int capacity) {
		super();
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.warehouseLocation = warehouseLocation;
		this.capacity = capacity;
	}


	public long getWarehouseId() {
		return warehouseId;
	}


	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}


	public String getWarehouseName() {
		return warehouseName;
	}


	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public String getWarehouseLocation() {
		return warehouseLocation;
	}


	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	@Override
	public String toString() {
		return "Warehouse [warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + ", warehouseLocation="
				+ warehouseLocation + ", capacity=" + capacity + "]";
	}
	
	
	
	
}
