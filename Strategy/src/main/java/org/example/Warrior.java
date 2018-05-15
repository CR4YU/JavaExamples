package org.example;

public class Warrior extends Unit {

	public Warrior() {
		attackBehaviour = new AttackWithSword();
		defenceBehaviour = new DefendWithShield();
	}

	@Override
	void display() {
		System.out.println("I'm a strong warrior. I carry two-handed sword and round shield.");
	}
}
