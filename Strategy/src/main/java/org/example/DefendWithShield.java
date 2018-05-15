package org.example;

public class DefendWithShield implements DefenceBehaviour {

	@Override
	public void defend() {
		System.out.println("Your attack has no power against my big steel shield.");
	}
}
