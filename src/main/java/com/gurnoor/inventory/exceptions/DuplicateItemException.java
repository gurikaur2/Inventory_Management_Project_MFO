package com.gurnoor.inventory.exceptions;


//Thrown when a duplicate cart entry would violate business rules(quantity exceeds stock)
public class DuplicateItemException extends RuntimeException{
	public DuplicateItemException(String message)
	{
		super(message);
	}
}
