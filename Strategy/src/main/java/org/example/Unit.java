package org.example;

public abstract class Unit {

	protected AttackBehaviour attackBehaviour;
	protected DefenceBehaviour defenceBehaviour;

	public abstract void display();

	public void die() {
		System.out.println("All units can die so I will die now. Goodbye my friends.");
	}

	public void performAttack() {
		attackBehaviour.attack();
	}

	public void performDefence() {
		defenceBehaviour.defend();
	}

	public void setAttackBehaviour(AttackBehaviour attackBehaviour) {
		this.attackBehaviour = attackBehaviour;
	}

	public void setDefenceBehaviour(DefenceBehaviour defenceBehaviour) {
		this.defenceBehaviour = defenceBehaviour;
	}
}
