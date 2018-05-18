package org.example.WeatherStation;

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
