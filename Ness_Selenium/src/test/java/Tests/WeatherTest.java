package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Weather.WeatherWebSite;
import Weather_API.WeatherFromAPI;

public class WeatherTest {

	@Test
	public void test() {

		Double apiWeather = Double.parseDouble(new WeatherFromAPI().getCurrentWeatherInWashington());
		Double guiWeather = Double.parseDouble(new WeatherWebSite().getCurrentWearFromGui());

		// Check that the gap between the results above is in range of 10%
		double gap = (Math.abs(guiWeather - apiWeather)) / guiWeather * 100;
		Assert.assertTrue(gap <= 10, "The gap between the results is bigger than 10%. " + "The weather.com value is "
				+ guiWeather + ", and the weather from the api is " + apiWeather);

	}

}
