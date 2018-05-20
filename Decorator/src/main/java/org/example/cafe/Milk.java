package org.example.cafe;

public class Milk extends Decorator {

	Beverage beverage;

	public Milk(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String description() {
		return beverage.description() + ", Milk";
	}

	@Override
	public double cost() {
		return 0.10 + beverage.cost();
	}
}
