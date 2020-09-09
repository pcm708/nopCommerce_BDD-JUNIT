package stepDefinitions;

import static utils.ConfigPropertyReader.getProperty;
import static utils.YamlReader.getYamlValue;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.DashboardPage;
import pages.LoginPage;
import utils.WebDriverFactory;

public class DashboardPageTest {
	protected WebDriver driver;
	protected WebDriverFactory wdFactory;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	
	@Before
//Cucumber : @Before will execute before every scenario
	public void setupLoginPageTest() {
		wdFactory = new WebDriverFactory();
		driver = wdFactory.getBrowserDriver(getProperty("browser"));
		loginPage = new LoginPage(driver);
	}
	
	@Given("^I launch the nopCommerce application$")
	public void i_launch_the_nopCommerce_application() throws Throwable {
		loginPage.launchApplication();
	}

	@When("^I login as admin with CORRECT credentials$")
	public void i_login_as_admin_with_CORRECT_credentials() throws Throwable {
		System.out.println("[Info]: Logging with Correct credentials");
		dashboardPage = loginPage.doLoginWith_CorrectCredentials();
	}

	@Then("^The webpage redirects to Dashboard Page Successfully$")
	public void the_webpage_redirects_to_Dashboard_Page() throws Throwable {
		Assert.assertEquals(getYamlValue("dashboardPageTitle"),dashboardPage.getPageTitle());
		System.out.println("[Assertion Passed]: Verified the dashboard page title");
	}
	
	@Then("^I click on the \"([^\"]*)\"$")
	public void i_click_on_the(String arg1) throws Throwable {
		String a[] = arg1.split(":");
		String pageHeader = dashboardPage.clickLeftTab(a[1].trim());
		Assert.assertEquals(a[1].trim(), pageHeader);
		System.out.println("[Assertion Passed]: Verified the "+a[1].trim()+" Page header");
	}
	
	@When("^I close the browser$")
	public void i_close_the_browser() throws Throwable {
		System.out.println("[Info]: Closing the browser session");
	}
	
	@After
//Cucumber : @After will execute after every scenario
	public void tearDown() {
		dashboardPage.close();
	}
}