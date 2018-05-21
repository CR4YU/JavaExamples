package org.example.cafe;

public class Soy extends Decorator {

	Beverage beverage;

	public Soy(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String description() {
		return beverage.description() + ", Soy";
	}

	@Override
	public double cost() {
		return 0.15 + beverage.cost();
	}
}