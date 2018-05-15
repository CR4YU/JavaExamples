package org.example;

public class Mage extends Unit {

	public Mage() {
		attackBehaviour = new AttackWithMagic();
		defenceBehaviour = new DefendWithMagicBarrier();
	}

	@Override
	void display() {
		System.out.println("Im a powerful mage with magic staff. I use spells to kill enemies.");
	}
}
