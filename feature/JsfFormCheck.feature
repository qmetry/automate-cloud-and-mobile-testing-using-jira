Feature: CustomerRegistrationForm

Scenario: Register customer
	Given User is on register customer page
	When User enters "John" as customer name
	And User enters "john@doe.com" as customer email
	And submit the form
	Then form is submitted successfully
	
Scenario: Verify registered customer details
	Given user is on register customer page
	When user fires the api to get customer details by generated token
	Then value in the name key in response is cust_name
	And value in email key in response is cust_email
	And user is navigated to login page