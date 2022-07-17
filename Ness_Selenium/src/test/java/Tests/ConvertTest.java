package Tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConvertTest {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.metric-conversions.org/");
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	@Test
	public void test_1() {
		convert("Celsius to Fahrenheit", 30);
	}

	@Test
	public void test_2() {
		convert("Meters to Feet", 100);
	}

	@Test
	public void test_3() {
		convert("Ounces to Grams", 20);
	}

	private void convert(String convertType, int valToConvert) {
		// 1.Select the wanted conversion method
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#queryFrom"))));
		driver.findElement(By.cssSelector("#queryFrom")).sendKeys(convertType.charAt(0) + "");

		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#results > ol> li"), 0));

		// use xpath contains to find the wanted conversion method
		WebElement ele = driver.findElement(By.xpath("//h2[contains(text(),'" + convertType + "')]//parent::li"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");

		// 2. Enter the value to be converted
		wait.until(ExpectedConditions.elementToBeClickable(ele.findElement(By.cssSelector(".argument"))))
				.sendKeys(valToConvert + "");

		wait.until(ExpectedConditions.elementToBeClickable(ele.findElement(By.cssSelector(".convert")))).click();

		// 3. Compare the actual result with the expected one
		String valueOnScreen = driver.findElement(By.cssSelector("#answer")).getText().split(" ")[1];
		double expectedValue = 0;
		System.out.println(valueOnScreen);
		if (convertType.equals("Celsius to Fahrenheit")) {
			expectedValue = valToConvert * 9 / 5 + 32;
			valueOnScreen = valueOnScreen.split("°F")[0];
		} else if (convertType.equals("Meters to Feet")) {
			expectedValue = valToConvert * 3.28;
			valueOnScreen = valueOnScreen.split("ft")[0];
		} else if (convertType.equals("Ounces to Grams")) {
			expectedValue = valToConvert * 28.3495231;
			valueOnScreen = valueOnScreen.split("g")[0];
		}
		long actualValue = Math.round(Double.parseDouble(valueOnScreen));
		expectedValue = Math.round(expectedValue);
		Assert.assertEquals(expectedValue, actualValue);

	}
}
