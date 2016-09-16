package cucumberselenuim.cucumberseleniumexample.customerRegistrationForm;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomerDataVerification {

	public static WebDriver driver;
	public static ObjectNode customerData;

	@Given("^user is on register customer page$")
	public void user_is_on_register_cust_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		driver = CustomerRegistrationForm.driver;
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.contains(CustomerRegistrationForm.moduleName));
	}

	@When("^user fires the api to get customer details by generated token$")
	public void row_addition_with_new_data() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// Grab token from UI
		String token = null;
		token = driver.findElement(By.id("token")).getText();
		System.out.println("Token: " + token);
		// Get customer data from API
		customerData = getCustomerData(token);
	}

	@Then("^value in the name key in response is cust_name$")
	public void verify_cust_name() throws Throwable {
		String nameOfUser = customerData.get("name").asText();
		System.out.println("Name of user: " + nameOfUser);
		Assert.assertTrue(nameOfUser.equals(CustomerRegistrationForm.name));
	}

	@Then("^value in email key in response is cust_email$")
	public void verify_cust_email() throws Throwable {
		String emailOfUser = customerData.get("email").asText();
		System.out.println("Email: " + emailOfUser);
		Assert.assertTrue(emailOfUser.equals(CustomerRegistrationForm.email));
	}

	@Then("^user is navigated to login page$")
	public void verify_user_page() throws Throwable {
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.contains("login-user"));
	}

	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private ObjectNode getCustomerData(String token) throws Exception {
		String url = CustomerRegistrationForm.baseUrl + "verify-token";
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		URIBuilder builder = new URIBuilder(request.getURI());
		for (Map.Entry<String, String> param : params.entrySet()) {
			builder.addParameter(param.getKey(), param.getValue());
		}
		((HttpRequestBase) request).setURI(builder.build());
		HttpResponse httpResponse = client.execute(request);

		System.out.println("Response Code : " + httpResponse.getStatusLine().getStatusCode());
		String response = new String(EntityUtils.toByteArray(httpResponse.getEntity()));
		System.out.println(response);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode respObj = mapper.readTree(response);
		ObjectNode customerDetails = JsonNodeFactory.instance.objectNode();
		if (respObj.get("isSuccess").asBoolean()) {
			customerDetails = (ObjectNode) respObj.get("data");
		} else {
			throw new Exception(respObj.get("errorMessage").asText());
		}
		return customerDetails;
	}
}