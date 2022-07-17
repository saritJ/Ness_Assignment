package Weather_API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

// This class gets the current temperature from API  
public class WeatherFromAPI {

	public String getCurrentWeatherInWashington() {
		/*
		 * These are the Latitude, Longitude of 20852, taken from
		 * https://www.freemaptools.com/convert-us-zip-code-to-lat-lng.htm
		 */

		String latitude = "39.0526";
		String longtitude = "-77.12315";
		String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longtitude
				+ "&hourly=temperature_2m";
		String responseContent = null;
		HttpURLConnection con = null;
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");

			int responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);
			BufferedReader in;

			if (responseCode <= 299) {// Success
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			responseContent = response.toString();
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		WeatherData weather_data = Parse(responseContent);
		String currentWeather = weather_data.getCurrentTemperature();
		return currentWeather;
	}

	private static WeatherData Parse(String json) {
		Gson gson = new Gson();
		WeatherData wd = gson.fromJson(json, WeatherData.class);
		return wd;
	}

}
