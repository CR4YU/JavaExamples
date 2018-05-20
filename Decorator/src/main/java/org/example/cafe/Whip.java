package org.example.cafe;
 
public class Whip extends Decorator {

	Beverage beverage;
 
	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String description() {
		return beverage.description() + ", Whip";
	}

	@Override
	public double cost() {
		return .10 + beverage.cost();
	}
}
