package org.example;

public class StrategyDemo {


	public static void main(String[] args) {
		Unit warrior = new Warrior();
		warrior.display();
		warrior.performAttack();
		warrior.performDefence();


		Unit mage = new Mage();
		mage.display();
		mage.performAttack();
		mage.performDefence();
	}

}
