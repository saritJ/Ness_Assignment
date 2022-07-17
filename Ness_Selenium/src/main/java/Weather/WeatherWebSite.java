package Weather;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WeatherWebSite {
	WebDriver driver;
	WebDriverWait wait;

	public String getCurrentWearFromGui() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://weather.com");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement el = wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#LocationSearch_input"))));

		el.sendKeys("20852");
		wait.until(
				ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#LocationSearch_listbox >button"), 0));

		WebElement ele = driver.findElement(By.xpath("//button[contains(text(),'ארצות הברית')]"));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		ele.click();

		String currentTemperature = driver.findElement(By.cssSelector("span.CurrentConditions--tempValue--3a50n"))
				.getText();
		System.out.println(currentTemperature);
		return currentTemperature.split("°")[0];
	}
}
