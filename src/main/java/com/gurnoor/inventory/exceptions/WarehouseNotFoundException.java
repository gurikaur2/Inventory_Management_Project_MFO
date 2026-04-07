package com.gurnoor.inventory.exceptions;

//Custom 404 exception for warehouses
public class WarehouseNotFoundException extends RuntimeException {
	public WarehouseNotFoundException(String message)
	{
		super(message);
	}
}
