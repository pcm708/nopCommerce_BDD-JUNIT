package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage{

	private By accountName = By.cssSelector(".account-info");

	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	public String getAccountName() {
		elementUtil.doIsDisplayed(accountName);
		return elementUtil.doGetText(accountName);
	}
}
