package org.example.cafe;

public abstract class Beverage {

	String description = "Unknown Beverage";
  
	public String description() {
		return description;
	}
 
	public abstract double cost();
}