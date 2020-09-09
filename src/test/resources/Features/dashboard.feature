Feature: Verifying the dashboard page functionality of nopCommerceBDD website
	
	@DashboardPage
	Scenario: Verify DashboardPage Title after successful Login.
	Given I launch the nopCommerce application
	When I login as admin with CORRECT credentials
	Then The webpage redirects to Dashboard Page Successfully
	Then I click on the "LeftTab: Dashboard"
	And I close the browser	