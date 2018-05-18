# Java Examples
Java examples and solutions of common problems.

## ReaderWriterProblem
Implementation of common computing problem in concurrency.  
Used `ReadWriteLock` - two related locks for writing and reading.

## ProducerConsumerProblemSimple
Another problem in concurrency.  
Threads are synchronised by `wait()`
and `notifyAll()` functions - that's why I called this solution _simple_.

## Singleton
Implementation of Singleton design pattern using double checked locking.  
Works correctly in concurrent environment.
```java
public class Singleton {

    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

## Strategy
Implementation of **Strategy** design pattern.  
Strategy pattern defines a set of algorithms 
that can be swapped at run time to carry out a specific behaviour.
This type of design pattern comes under behavior pattern.  


Abstract class `Unit` has two fields:
```java
AttackBehaviour attackBehaviour;
DefenceBehaviour defenceBehaviour;
```
And methods to perform specific behaviours:
```java
public void performAttack() {
	attackBehaviour.attack();
}

public void performDefence() {
	defenceBehaviour.defend();
}
```
`AttackBehaviour` and `DefenceBehaviour` are interfaces:
```java
public interface AttackBehaviour {
	void attack();
}
```
```java
public interface DefenceBehaviour {
	void defend();
}
```
Implementation of `AttackBehaviour` example:
```java
public class AttackWithSword implements AttackBehaviour {
	public void attack() {
		System.out.println("I will use my sword to cut my enemies.");
	}
}
```
Now we extend the `Unit` class and assigning specific behaviours.
```java
public class Warrior extends Unit {
	public Warrior() {
		attackBehaviour = new AttackWithSword();
		defenceBehaviour = new DefendWithShield();
	}
}
```
Testing behaviours of `Warrior`:
```java
public class StrategyDemo {
	public static void main(String[] args) {
		Unit warrior = new Warrior();
		warrior.performAttack();
		warrior.performDefence();
}
```
Results:
````
>I will use my swords to cut my enemies.
>Your attack has no power against my big steel shield.
````
We can easily assign any other behaviour to warrior:
```java
warrior.setAttackBehaviour(new AttackWithBow());
```
