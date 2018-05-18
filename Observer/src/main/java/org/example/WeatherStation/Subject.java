package org.example.WeatherStation;

public interface Subject {

	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers();
}
