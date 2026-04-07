package com.gurnoor.inventory.exceptions;

//Custom 404 exception for inventory items
public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(String message)
	{
		super(message);
	}
}
