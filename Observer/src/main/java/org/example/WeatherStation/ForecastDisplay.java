package org.example.WeatherStation;

public class ForecastDisplay implements Observer, DisplayWeather {

	private float currentPressure = 29.92f;  
	private float lastPressure;
	private WeatherData weatherData;

	public ForecastDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	public void update(WeatherConditions weatherConditions) {
		lastPressure = currentPressure;
		currentPressure = weatherConditions.getPressure();

		display();
	}

	public void display() {
		System.out.print("Forecast: ");
		if (currentPressure > lastPressure) {
			System.out.println("More pressure, better weather");
		} else if (currentPressure == lastPressure) {
			System.out.println("Same pressure as before");
		} else if (currentPressure < lastPressure) {
			System.out.println("Less pressure, rainy weather");
		}
	}
}
