package Weather_API;

// This class converts the weather data json from the API to an object
public class WeatherData {

	public currentTemperature hourly;

	public String getCurrentTemperature() {
		return hourly.getTemperature_2m()[0];
	}

	public void setHourly(currentTemperature hourly) {
		this.hourly = hourly;
	}

}
