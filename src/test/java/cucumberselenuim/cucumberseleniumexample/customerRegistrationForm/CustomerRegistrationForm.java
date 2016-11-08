package cucumberselenuim.cucumberseleniumexample.customerRegistrationForm;

import java.net.URL;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;

public class CustomerRegistrationForm {

	public static WebDriver driver;
	public static String name;
	public static String email;
	public static String baseUrl = "http://54.167.106.13:7070/customerRegistration/";
	public static String moduleName = "customerRegistration";

	@Given("^User is on register customer page$")
	public void user_is_on_customer_Page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Platform platform = Platform.MAC;
		String chromeDriverPath = "deps//chromedriver_mac32//chromedriver";

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		DesiredCapabilities capabilities = DesiredCapabilities.android();
		capabilities.setCapability("device", "Android");
		capabilities.setCapability("app", "Chrome");
		capabilities.setCapability("deviceName", "AndroidDevice");
		capabilities.setCapability(CapabilityType.VERSION, "5.0");
		capabilities.setCapability(CapabilityType.PLATFORM, "Android");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		capabilities.setPlatform(platform);

		driver = new AndroidDriver(new URL("http://0.0.0.0:4724/wd/hub"), capabilities);
		// To test on Desktop, uncomment the below line and comment desired capabilities and above line
//		 driver = new ChromeDriver();
		Thread.sleep(10000);

		driver.get(baseUrl);
	}

	@When("^User enters \"([^\"]*)\" as customer name$")
	public void user_enters_name(String name) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		CustomerRegistrationForm.name = name;
		driver.findElement(By.id("name")).sendKeys(name);
	}

	@When("^User enters \"([^\"]*)\" as customer email$")
	public void user_enters_address(String email) throws Throwable {
		CustomerRegistrationForm.email = email;
		driver.findElement(By.id("email")).sendKeys(email);
	}

	@When("^submit the form$")
	public void user_submits_form() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("submit")).click();
		Thread.sleep(5000);
	}

	@Then("^form is submitted successfully$")
	public void user_navigated_to_homepage() throws Throwable {
		Assert.assertTrue(driver.getCurrentUrl().contains(moduleName));
	}

}