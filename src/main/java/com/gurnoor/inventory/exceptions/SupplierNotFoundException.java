package com.gurnoor.inventory.exceptions;

//Custom 404 exception for suppliers
public class SupplierNotFoundException extends RuntimeException {
	public SupplierNotFoundException(String message)
	{
		super(message);
	}
}
