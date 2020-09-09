Feature: Verifying the login functionality of nopCommerceBDD website
	
	@LoginPage
	Scenario: Login from admin account with incorrect credentials.
	Given I launch the nopCommerce application
	And I verify the Login Page title
	When I login as admin with INCORRECT credentials, I get error message on the Screen
	And I close the browser
	
	@LoginPage
	Scenario: Login from admin account with correct credentials.
	Given I launch the nopCommerce application
	And I verify the Login Page title
	When I login as admin with CORRECT credentials
	Then The webpage redirects to Dashboard Page Successfully
	And I close the browser
	
	
	