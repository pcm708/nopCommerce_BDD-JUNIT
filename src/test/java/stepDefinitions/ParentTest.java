//package stepDefinitions;
//
//import static utils.ConfigPropertyReader.getProperty;
//
//import pages.BasePage;
//import utils.WebDriverFactory;
//import cucumber.api.java.After;
//import cucumber.api.java.Before;
//import org.openqa.selenium.WebDriver;
//
//public class ParentTest {
//	protected WebDriver driver =null;
//	protected WebDriverFactory wdFactory;
//	BasePage basePage;
//	
//	@Before
//	public void setup() {
//		wdFactory  = new WebDriverFactory();
//		driver = wdFactory.getBrowserDriver(getProperty("browser"));
//		basePage = new BasePage(driver);
//	}
//	
//	@After
//	public void tearDown() {
//		basePage.close();
//	}
//	
//}