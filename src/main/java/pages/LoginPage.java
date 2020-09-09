package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import static utils.ConfigPropertyReader.getProperty;

public class LoginPage extends BasePage{

	private By emailAddress = By.cssSelector("#Email");
	private By password= By.cssSelector("#Password");
//	private By loginButton = By.cssSelector("[value='Log in']");
	private By invalidLoginError = By.cssSelector(".message-error.validation-summary-errors");
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public DashboardPage doLoginWith_CorrectCredentials() {
		elementUtil.doIsDisplayed(emailAddress);
		elementUtil.doSendKeys(emailAddress, getProperty("username"));
		elementUtil.doSendKeys(password, getProperty("password"));
		elementUtil.doActionsSendKeys(Keys.ENTER);
		return new DashboardPage(driver);
	}
	
	public boolean doLoginWith_INCorrectCredentials() {
		elementUtil.doIsDisplayed(emailAddress);
		elementUtil.doSendKeys(emailAddress, getProperty("username"));
		elementUtil.doSendKeys(password, getProperty("password")+"1");
		elementUtil.doActionsSendKeys(Keys.ENTER);
		return elementUtil.doIsDisplayed(invalidLoginError);
	}
}