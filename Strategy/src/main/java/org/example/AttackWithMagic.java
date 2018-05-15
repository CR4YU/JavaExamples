package org.example;

public class AttackWithMagic implements AttackBehaviour {

	@Override
	public void attack() {
		System.out.println("I need a second to throw fireball towards you. You are dead.");
	}
}
