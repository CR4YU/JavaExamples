package org.example.ducks;
import java.util.Random;

public class DuckAdapter implements Turkey {

	Duck duck;
 
	public DuckAdapter(Duck duck) {
		this.duck = duck;
	}
    
	public void gobble() {
		duck.quack();
	}
  
	public void fly() {
		if (new Random().nextInt(5) == 0) {
		     duck.fly();
		}
	}
}
