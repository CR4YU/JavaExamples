package org.example.WeatherStation;

import java.util.*;

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
