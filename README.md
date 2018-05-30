# Java Examples
Java examples and solutions of common problems.

## ReaderWriterProblem
Implementation of common computing problem in concurrency.  
Used `ReadWriteLock` - two related locks for writing and reading.

`Writer` thread:
```java
public void run() {
	while(true) {
		lock.writeLock().lock();
		try {
			int newValue = generateValue();
			sharedObj.write(newValue);
			logWriteMade(newValue);
		} finally {
			lock.writeLock().unlock();
		}
		waitRandomTime();
	}
}
```
At first writer thread locks a write lock, then generates a value (int in this example)
and then assigns it to shared object. At the end thread releases the lock and waits
some time before next while loop iteration.

`Reader` thread:
```java
public void run() {
	while(true) {
		lock.readLock().lock();
		try {
			int newValue = sharedObj.read();
			logReadMade(newValue);
		} finally {
			lock.readLock().unlock();
		}
		waitRandomTime();
	}
}
``` 
Reader thread is very similar. Main loop takes following steps: acquires read lock,
reads value from shared object and finally releases the lock. 


## ProducerConsumerProblemSimple
Another problem in concurrency.  
Threads are synchronised by `wait()`
and `notifyAll()` functions - that's why I called this solution _simple_.

`Producer` thread:

```java
public void run() {
	while(true) {
		Object newOBj = requestNewObject();
		synchronized (objList) {
			waitUntilProductionPossible();
			objList.add(newOBj);
			objList.notifyAll();
			logNewObjectProduced(newOBj);
		}
	}
}

private void waitUntilProductionPossible() {
	while(objList.size() == LIST_CAPACITY) {
		try {
			objList.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
```
Producer creates new object and adds to shared queue.

`Consumer` thread works similarly:
```java
public void run() {
	while(true) {
		Object obj = null;
		synchronized (objList) {
			waitUntilConsumptionPossible();
			obj = objList.remove(0);
			objList.notifyAll();
			logConsumptionMade(obj);
		}
		processObject(obj);
	}
}
private void waitUntilConsumptionPossible() {
	while(objList.isEmpty()) {
		try {
			objList.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
```





## Singleton
Implementation of **Singleton** design pattern using double checked locking.  
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


## Observer
Implementation of **Observer** design pattern.

Observer pattern is used when there is one-to-many 
relationship between objects such as if one object is
modified, its depenedent objects are to be notified
automatically. Observer pattern is one of behavioral
patterns.


At first step we create `Subject` and `Observer` interfaces:
```java
public interface Subject {
	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers();
}
```
```java
public interface Observer {
	void update(WeatherConditions weatherConditions);
}
```

Our observed subject is `WeatherData`.
```java
public class WeatherData implements Subject {

	private List<Observer> observers;
	private WeatherConditions weatherConditions;

	public WeatherData() {
		observers = new ArrayList<>();
		weatherConditions = new WeatherConditions();
	}
	
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	public void notifyObservers() {
		observers.forEach(o -> o.update(weatherConditions));
	}
	
	public void measurementsChanged() {
		notifyObservers();
	}
	
	public void setMeasurements(float temperature, float humidity, float pressure) {
		weatherConditions.setTemperature(temperature);
		weatherConditions.setHumidity(humidity);
		weatherConditions.setPressure(pressure);

		measurementsChanged();
	}
}
```

Observed subject has list of its observers and methods to 
manage them (register/remove). Crucial method is `notifyObservers()` to tell
all observers _"Hey everybody, my state has changed so I give you
some new data"_. Every observer uses this data his own way. For example:

```java
public class CurrentWeatherConditionsDisplay implements Observer, DisplayWeather {
	
	private float temperature;
	private float humidity;
	private Subject weatherData;
	
	public CurrentWeatherConditionsDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}
	
	public void update(WeatherConditions weatherConditions) {
		this.temperature = weatherConditions.getTemperature();
		this.humidity = weatherConditions.getHumidity();
		display();
	}
	
	public void display() {
		System.out.println("Current conditions: " + temperature 
			+ "F degrees and " + humidity + "% humidity");
	}
}
```

As you can see this observer takes two values from `WeatherConditions` object and
displays them his way.  
Other observer takes only temperature value and displays
statistics:
```java
public class StatisticsDisplay implements Observer, DisplayWeather {

	private float maxTemp = 0.0f;
	private float minTemp = 200;
	private float tempSum = 0.0f;
	private int numReadings = 0;
	private WeatherData weatherData;

	public StatisticsDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	public void update(WeatherConditions weatherConditions) {
		float temp = weatherConditions.getTemperature();
		tempSum += temp;
		numReadings++;

		if (temp > maxTemp) {
			maxTemp = temp;
		}
 
		if (temp < minTemp) {
			minTemp = temp;
		}

		display();
	}

	public void display() {
		System.out.println("Avg | Max | Min temperature = " +
				avg() + "|" + maxTemp + "|" + minTemp);
	}

	private float avg() {
		return tempSum / numReadings;
	}
}
```

Now if we would like to create new observer all we have to do is
implement `Observer` interface, register as observer to observed subject
and create method `update()` which is
executed every time the subject changes his state.

Let's run a simple demo:
```java
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
	
		new CurrentWeatherConditionsDisplay(weatherData);
		new StatisticsDisplay(weatherData);
		new ForecastDisplay(weatherData);
		new HeatIndexDisplay(weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
	}
```

Result:
````
>Current conditions: 80.0F degrees and 65.0% humidity
>Avg | Max | Min temperature = 80.0|80.0|80.0
>Forecast: More pressure, better weather
>Heat index: 82.95535
>Current conditions: 82.0F degrees and 70.0% humidity
>Avg | Max | Min temperature = 81.0|82.0|80.0
>Forecast: Less pressure, rainy weather
>Heat index: 86.90124
>Current conditions: 78.0F degrees and 90.0% humidity
>Avg | Max | Min temperature = 80.0|82.0|78.0
>Forecast: Same pressure as before
>Heat index: 83.64967
````


## Decorator
Implementation of **Decorator** design pattern.

This pattern allows to add new functionality to an existing object 
without editing its structure.
Decorator comes under structural pattern. It acts like 
wrapper to existing class.

At first we create supertype for both decorators and decorated classes.
```java
public abstract class Beverage {

	String description = "Unknown Beverage";
  
	public String description() {
		return description;
	}
 
	public abstract double cost();
}
```
Now we create class that will be decorated. Every coffee type has its own price.
```java
public class Cappuccino extends Beverage {

	public Cappuccino() {
		description = "Cappuccino Coffee";
	}

	@Override
	public double cost() {
		return 0.89;
	}
}
```
Now `Decorator` - abstract class extending `Beverage`.
```java
public abstract class Decorator extends Beverage {
	public abstract String description();
}
```
And finally we create specific decorators.
```java
public class Milk extends Decorator {

	Beverage beverage;

	public Milk(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String description() {
		return beverage.description() + ", Milk";
	}

	@Override
	public double cost() {
		return 0.10 + beverage.cost();
	}
}
```
Every decorator contains reference to decorated object.

Let's test cafe:
```java
public class CoffeeDemo {
 
	public static void main(String args[]) {
		Beverage beverage = new Espresso();
		System.out.println(beverage.description() + " $" + beverage.cost());
 
		Beverage beverage2 = new DarkRoast();
		beverage2 = new Mocha(beverage2);
		beverage2 = new Mocha(beverage2);
		beverage2 = new Whip(beverage2);
		System.out.println(beverage2.description() + " $" + beverage2.cost());
 
		Beverage beverage3 = new Cappuccino();
		beverage3 = new Soy(beverage3);
		beverage3 = new Mocha(beverage3);
		beverage3 = new Whip(beverage3);
		System.out.println(beverage3.description() + " $" + beverage3.cost());
	}
}
```
Take a look at needed steps to create and decorate coffees. At first we create specific type
of coffee e.g. `Cappuccino`. Then we wrap the coffee into decorator:
```java
beverage3 = new Mocha(beverage3);
```
Now if we call `cost()` method on that coffee it will return cost of coffee plus
cost of decorator (mocha).

Result of running demo:
````
>Espresso $1.99
>Dark Roast Coffee, Mocha, Mocha, Whip $1.49
>Cappuccino Coffee, Soy, Mocha, Whip $1.34
````

## Factory method
Implementation of **Factory Method** design pattern.

This pattern is one of the most used design patterns in Java.
Factory pattern comes under creational pattern -
it's one of the best ways to create an objects.

In Factory pattern, we keep object creation logic
 from client and 
refer to created object using a common interface.

First step is to create two abstract classes.
```java
public abstract class Pizza {

	String name;
	String dough;
	String sauce;
	ArrayList<String> toppings = new ArrayList<>();
 
	void prepare() {
		System.out.println("Preparing " + name);
	}
  
	void bake() {
		System.out.println("Bake for 25 minutes at 350");
	}
 
	void cut() {
		System.out.println("Cutting the pizza into diagonal slices");
	}
  
	void box() {
		System.out.println("Place pizza in official PizzaStore box");
	}
 
}
```
We want to make many kinds of pizza. Of course every pizza has it's name,
must be cut or boxed. Now we want abstract class that represents pizza store.
```java
public abstract class PizzaStore {
 
	abstract Pizza createPizza(String item);
 
	public Pizza orderPizza(String type) {
		Pizza pizza = createPizza(type);
		System.out.println("--- Making a " + pizza.getName() + " ---");
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
}
```
Every pizza store has its own pizza kinds. We will implement two
different pizza stores: `ChicacoPizzaStore` and `NYPizzaStore`.
Every store has to implement its own version of factory method.
```java
public class ChicagoPizzaStore extends PizzaStore {

	Pizza createPizza(String item) {
		switch (item) {
			case "cheese":
				return new ChicagoStyleCheesePizza();
			case "veggie":
				return new ChicagoStyleVeggiePizza();
			case "clam":
				return new ChicagoStyleClamPizza();
			case "pepperoni":
				return new ChicagoStylePepperoniPizza();
		}
		return null;
	}
}
```
`createPizza()` is our factory method. It returns reference to the
particular pizza type according to the given string parameter.
Below there is example of `ChicagoStyleCheesePizza`.
```java
public class ChicagoStyleCheesePizza extends Pizza {

	public ChicagoStyleCheesePizza() { 
		name = "Chicago Style Deep Dish Cheese Pizza";
		dough = "Extra Thick Crust Dough";
		sauce = "Plum Tomato Sauce";
 
		toppings.add("Shredded Mozzarella Cheese");
	}
 
	void cut() {
		System.out.println("Cutting the pizza into square slices");
	}
}
```
Testing pizza stores:
```java
public class PizzaTestDrive {
 
	public static void main(String[] args) {
		PizzaStore nyStore = new NYPizzaStore();
		PizzaStore chicagoStore = new ChicagoPizzaStore();
 
		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println("Ethan ordered a " + pizza.getName() + "\n");
 
		pizza = chicagoStore.orderPizza("cheese");
		System.out.println("Joel ordered a " + pizza.getName() + "\n");

	}
}
```
Results:
````
>--- Making a NY Style Sauce and Cheese Pizza ---
>Preparing NY Style Sauce and Cheese Pizza
>Tossing dough...
>Adding sauce...
>Adding toppings: 
>Grated Reggiano Cheese 
>Bake for 25 minutes at 350
>Cutting the pizza into diagonal slices
>Place pizza in official PizzaStore box
>Ethan ordered a NY Style Sauce and Cheese Pizza

>--- Making a Chicago Style Deep Dish Cheese Pizza ---
>Preparing Chicago Style Deep Dish Cheese Pizza
>Tossing dough...
>Adding sauce...
>Adding toppings: 
>Shredded Mozzarella Cheese 
>Bake for 25 minutes at 350
>Cutting the pizza into square slices
>Place pizza in official PizzaStore box
>Joel ordered a Chicago Style Deep Dish Cheese Pizza
````

## AbstractFactory
Implementation of **Abstract factory** design pattern.

Type of creational design pattern. It uses super-factory to
create other factories so it's usually called _factory of factories_.

In Abstract Factory pattern an interface is responsible
 for creating a factory of related objects without
  explicitly specifying their classes. Each generated 
factory can give the objects as per the Factory pattern.

## SpringBootAppContacts
Simple **Spring Boot** application to manage contact list. All contacts are stored
in **H2** Database. For presentation layer I used **thymeleaf** - modern server-side 
Java template engine.
![Alt text](images/contacts.PNG?raw=true "Screenshot")

## SpringBootRestAppMusicLibrary
Simple REST service implemented with **Spring Boot**.

Application allows to manage music library using HTTP requests.
There are two methods:
* GET - get all artists with their albums
* POST - add new artist with his albums

As database used **H2**. For ORM used **Hibernate**.

After running Spring Boot application we can test our REST method:
![Alt text](images/music_library_get.png?raw=true "Screenshot")















