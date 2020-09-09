package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.*;
import static utils.ConfigPropertyReader.*;

public class BasePage {

	protected WebDriver driver = null;
	protected ElementUtil elementUtil;
	protected JavaScriptUtil jsUtil;
	protected By pageHeader = By.cssSelector(".content-header h1");
	protected By leftMenuTab = By.xpath("//span[text()='${tabName}']");
	
	
		public BasePage(WebDriver driver) {
			this.driver = driver;
			elementUtil = new ElementUtil(this.driver);
			jsUtil = new JavaScriptUtil(this.driver);
		}

		public void launchApplication() {
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get(getProperty("url"));
		}
		
		public String getPageTitle() {
			jsUtil.checkPageIsReady();
			return driver.getTitle();
		}
		
		public String clickLeftTab(String tabName) {
			elementUtil.doClick(leftMenuTab, tabName);
			//Verifying if the page title contains the same tabname header
			 jsUtil.checkPageIsReady();
			 return elementUtil.doGetText(pageHeader);
		}
		
		public void quit() {
			driver.quit();
		}
		
		public void close() {
			driver.close();
		}
}