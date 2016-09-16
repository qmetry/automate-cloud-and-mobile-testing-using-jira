package cucumberselenuim.cucumberseleniumexample;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

		features = "feature/FormCheck.feature", 
		glue = { "cucumberselenuim.cucumberseleniumexample.customerRegistrationForm" }, 
		plugin = {"com.infostretch.qmetrytestmanager.result.TestExecution", "json:target/cucumber.json" }, 
		monochrome = true
	)
public class TestRunner {

}
