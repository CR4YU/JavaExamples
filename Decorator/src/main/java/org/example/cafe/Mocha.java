package org.example.cafe;

public class Mocha extends Decorator {

	Beverage beverage;
 
	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String description() {
		return beverage.description() + ", Mocha";
	}

	@Override
	public double cost() {
		return 0.20 + beverage.cost();
	}
}