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

## SpringBootAppContacts
Simple **Spring Boot** application to manage contact list. All contacts are stored
in **H2** Database. For presentation layer I used **thymeleaf** - modern server-side 
Java template engine.
![Alt text](images/contacts.png?raw=true "Screenshot")






