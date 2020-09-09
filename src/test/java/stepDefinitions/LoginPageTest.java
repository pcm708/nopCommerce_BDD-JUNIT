//package stepDefinitions;
//
//import static utils.YamlReader.getYamlValue;
//
//import org.junit.Assert;
//import org.openqa.selenium.WebDriver;
//import static utils.ConfigPropertyReader.getProperty;
//
//import cucumber.api.java.After;
//import cucumber.api.java.Before;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//import pages.DashboardPage;
//import pages.LoginPage;
//import utils.WebDriverFactory;
//
//public class LoginPageTest {
//	
//	protected WebDriver driver =null;
//	protected WebDriverFactory wdFactory;
//	LoginPage loginPage;
//	DashboardPage dashboardPage;
//	
//	@Before
////	Cucumber : @Before will execute before every scenario
//	public void setupLoginPageTest() {
//		wdFactory  = new WebDriverFactory();
//		driver = wdFactory.getBrowserDriver(getProperty("browser"));
//		loginPage = new LoginPage(driver);
//	}
//	
//	@Given("^I launch the nopCommerce application$")
//	public void i_launch_the_nopCommerce_application() throws Throwable {
//		loginPage.launchApplication();
//	}
//
//	@Given("^I verify the Login Page title$")
//	public void i_verify_the_Login_Page_title() throws Throwable {
//		Assert.assertEquals(getYamlValue("loginPageTitle"),loginPage.getPageTitle());
//		System.out.println("[Assertion Passed]: Verified the login page title");
//	}
//
//	@When("^I login as admin with INCORRECT credentials, I get error message on the Screen$")
//	public void i_login_as_admin_with_INCORRECT_credentials() throws Throwable {
//		System.out.println("[Info]: Logging with INcorrect credentials");
//		Assert.assertTrue(loginPage.doLoginWith_INCorrectCredentials());
//		System.out.println("[Assertion Passed]: Invalid credentials error message is displayed");
//	}
//	
//	@When("^I close the browser$")
//	public void i_close_the_browser() throws Throwable {
//		System.out.println("[Info]: Closing the browser session");
//	}
//	
//	@When("^I login as admin with CORRECT credentials$")
//	public void i_login_as_admin_with_CORRECT_credentials() throws Throwable {
//		System.out.println("[Info]: Logging with Correct credentials");
//		dashboardPage = loginPage.doLoginWith_CorrectCredentials();
//	}
//
//	@Then("^The webpage redirects to Dashboard Page Successfully$")
//	public void the_webpage_redirects_to_Dashboard_Page() throws Throwable {
//		Assert.assertEquals(getYamlValue("dashboardPageTitle"),dashboardPage.getPageTitle());
//		System.out.println("[Assertion Passed]: Verified the dashboard page title");
//	}
//	
//	@After
////	Cucumber : @After will execute after every scenario
//	public void tearDown() {
//		loginPage.close();
//	}
//}