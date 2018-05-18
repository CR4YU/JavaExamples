package org.example.WeatherStation;
	
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
