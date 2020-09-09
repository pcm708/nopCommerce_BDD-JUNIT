package utils;

import java.util.ArrayList;
import java.util.List;
import static utils.ConfigPropertyReader.getProperty;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {

	WebDriver driver;
	JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(this.driver);
	}

	public List<WebElement> getElements(By locator) {
		List<WebElement> elementsList = driver.findElements(locator);
		return elementsList;
	}
	
	public List<WebElement> getElements(By locator, String replacement) {
		By newLocator = locParser(locator,replacement);
		List<WebElement> elementsList = driver.findElements(newLocator);
		return elementsList;
	}
	
	private By getLocators(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType.replace("By.",""))) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case cssSelector:
			return By.cssSelector(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}
	
	public WebElement getElement(By locator) {
		WebElement element = null;
		System.out.println("Locator: " + locator);
		element = driver.findElement(locator);
			if (getProperty("highlight").equalsIgnoreCase("yes")||getProperty("highlight").equalsIgnoreCase("true")) 
				jsUtil.flash(element);
		return element;
	}
	
	public WebElement getElement(By locator,String replacement) {
		WebElement element = null;
		try {
			System.out.println("Locator: " + locator);
			element = driver.findElement(locParser(locator,replacement));
			if (getProperty("highlight").equalsIgnoreCase("yes")) {
				jsUtil.flash(element);
			}
		} catch (Exception e) {
			System.out.println("Some exception got occurred with this locator: " + locator);
		}
		return element;
	}
	
	private By locParser(By locator,String replacement) {
		try{
			String[] loc = locator.toString().split(":",2);
			loc[1] = loc[1].replaceAll("\\$\\{.+?\\}", replacement);
			return getLocators(loc[0].trim(), loc[1].trim());
		}catch(NullPointerException e) {
			throw new NoSuchElementException("");
		}
	}
	
	private By locParser(By locator,String replacement1, String replacement2) {
		try{
			String[] loc = locator.toString().split(":",2);
			String update1 = loc[1].replaceFirst("\\$\\{.+?\\}", replacement1);
			String update2 = update1.replaceAll("\\$\\{.+?\\}", replacement2);
			return getLocators(loc[0].trim(), update2);
		}catch(NullPointerException e) {
			throw new NoSuchElementException("");
		}
	}

	public void hardWait(int seconds) throws InterruptedException {
		Thread.sleep(seconds*1000);
	}
	
	public void switchToDefaultFrame() {
		try {
			driver.switchTo().defaultContent();
			hardWait(1);
		} catch (Exception e) {
		}
	}
	
	public void switchToSpecificFrame(By locator) {
		switchToDefaultFrame();
		waitForElementToBeVisible(locator, 20);
		driver.switchTo().frame(getElement(locator));
	}
	
	public void doSendKeys(By locator, String value) {
		waitForElementPresent(locator, 20);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		waitForElementPresent(locator, 20);
		getElement(locator).click();
	}
	
	public void doClick(By locator,String replacement) {
		By newLocator = locParser(locator,replacement);
		waitForElementPresent(newLocator, 20);
		getElement(newLocator).click();
	}
	
	public String doGetText(By locator) {
		waitForElementPresent(locator, 20);
		return getElement(locator).getText();
	}
	
	public String doGetText(By locator, String replacement) {
		By newLocator = locParser(locator,replacement);
		waitForElementPresent(newLocator, 20);
		return getElement(newLocator).getText();
	}
	
	public String doGetText(By locator, String replacement1, String replacement2) {
		By newLocator = locParser(locator,replacement1, replacement2);
		waitForElementPresent(newLocator, 20);
		return getElement(newLocator).getText();
	}

	public boolean doIsDisplayed(By locator) {
		waitForElementPresent(locator, 20);
		return getElement(locator).isDisplayed();
	}
	
	public boolean doIsDisplayed(By locator,int timeout) {
		try {
			waitForElementPresent(locator, timeout);
			return getElement(locator).isDisplayed();
		}catch(NoSuchElementException e) {
			System.out.println("ERROR!!! No element found on the page w.r.t. this locator: "+locator);
			return false;
		}
	}
	
	public boolean doIsDisplayed(By locator,String replacement) {
		By newLocator = locParser(locator,replacement);
		if(waitForElementPresent(newLocator, 20)==null) return false;
			return getElement(newLocator).isDisplayed();
	}
	
	public boolean doIsDisplayed(By locator,String replacement, int timeout) {
		By newLocator = locParser(locator,replacement);
		if(waitForElementPresent(newLocator, timeout)==null) return false;
			return getElement(newLocator).isDisplayed();
		}
	
	public boolean checkIfPresent(By locator, int timeout) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return getElement(locator).isDisplayed();
		}catch(NoSuchElementException e) {
			System.out.println("No Such element present on the Page: "+locator);
		}catch(TimeoutException e) {
			System.out.println("Driver failed to find "+locator+" within "+timeout+" seconds");
		}
		return false;
	}
	
	public boolean checkIfPresent(By locator, String replacement, int timeout) {
		By newLocator = locParser(locator,replacement);
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(newLocator));
			return getElement(newLocator).isDisplayed();
		}catch(NoSuchElementException e) {
			System.out.println("No Such element present on the Page: "+newLocator);
		}catch(TimeoutException e) {
			System.out.println("Driver failed to find "+newLocator+" within "+timeout+" seconds");
		}
		return false;
	}
	
	// **********************************Drop Down Utils
	// *********************************

	public void doSelectByVisibleText(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int doDropDownOptionsCount(By locator) {
		return doGetDropDownOptions(locator).size();
	}

	public ArrayList<String> doGetDropDownOptions(By locator) {
		ArrayList<String> ar = new ArrayList<String>();
		Select select = new Select(getElement(locator));
		List<WebElement> OptionsList = select.getOptions();

		for (int i = 0; i < OptionsList.size(); i++) {
			String text = OptionsList.get(i).getText();
			ar.add(text);
		}
		return ar;
	}

	public void doSelectDropDownValue(By locator, String value) {
		Select selectday = new Select(getElement(locator));
		List<WebElement> OptionsList = selectday.getOptions();

		for (int i = 0; i < OptionsList.size(); i++) {
			String text = OptionsList.get(i).getText();
			if (text.equals(value)) {
				OptionsList.get(i).click();
				break;
			}
		}
	}

	public void doSelectDropDownValueWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		for (int i = 0; i < optionsList.size(); i++) {
			String text = optionsList.get(i).getText();
			if (text.equals(value)) {
				optionsList.get(i).click();
				break;
			}
		}
	}

	public void selectChoiceValues(By locator, String... value) {
		// List<WebElement> choiceList =
		// driver.findElements(By.cssSelector("span.comboTreeItemTitle"));
		List<WebElement> choiceList = getElements(locator);

		if (!value[0].equalsIgnoreCase("ALL")) {

			for (int i = 0; i < choiceList.size(); i++) {
				String text = choiceList.get(i).getText();
				System.out.println(text);

				for (int k = 0; k < value.length; k++) {
					if (text.equals(value[k])) {
						choiceList.get(i).click();
						break;
					}
				}

			}
		}
		// select all the values:
		else {
			try {
				for (int all = 0; all < choiceList.size(); all++) {
					choiceList.get(all).click();
				}
			} catch (Exception e) {

			}
		}
	}

	// **********************************Actions class Utils
	// *********************************

	public void doDragAndDrop(By source, By target) {
		Actions action = new Actions(driver);
		WebElement sourceEle = getElement(source);
		WebElement targetEle = getElement(target);
		action.dragAndDrop(sourceEle, targetEle).build().perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions action = new Actions(driver);
		action.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsSendKeys(Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(key).build().perform();
	}
	
	public void doActionsClick(By locator) {
		waitForElementPresent(locator, 20);
		Actions action = new Actions(driver);
		action.click(getElement(locator)).build().perform();
	}
	
	public void doActionsClick(By locator, int timeout) {
		waitForElementPresent(locator, timeout);
		Actions action = new Actions(driver);
		action.click(getElement(locator)).build().perform();
	}
	
	public void doActionsClick(By locator, String replacement) {
		By newLocator = locParser(locator,replacement);
		waitForElementPresent(newLocator, 20);
		Actions action = new Actions(driver);
		action.click(getElement(newLocator)).build().perform();
	}
	
	public void doActionsClick(By locator, String replacement,int timeout) {
		By newLocator = locParser(locator,replacement);
		waitForElementPresent(newLocator, timeout);
		Actions action = new Actions(driver);
		action.click(getElement(newLocator)).build().perform();
	}

	// ***************************** Wait Utils
	// *******************************************

	public List<WebElement> visibilityofAllElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public WebElement waitForElementPresent(By locator, int timeout) {
		WebElement element = null;
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return element;
		}catch(NoSuchElementException e) {
			throw new NoSuchElementException("\nERROR!!! No Such element present on the Page: "+locator);
		}catch(TimeoutException e) {
			throw new TimeoutException("\nTIMEOUT!!! Driver failed to find element within "+timeout+" seconds: "+locator);
		}
	}
	
	public WebElement waitForElementPresent(By locator,String replacement, int timeout) {
		By newLocator = locParser(locator,replacement);
		return waitForElementPresent(newLocator,timeout);
	}
	
	public WebElement waitForElementToBeVisible(By locator, int timeout) {
		WebElement element = getElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
		jsUtil.flash(element);
		return element;
	}
	
	public WebElement waitForElementToBeVisible(By locator, String replacement, int timeout) {
		By newLocator = locParser(locator,replacement);
		WebElement element = getElement(newLocator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
		jsUtil.flash(element);
		return element;
	}
	
	public boolean waitForElementToBeInVisible(By locator,int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

	public WebElement waitForElementToBeClickable(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public WebElement waitForElementToBeClickable(By locator, String replacement, int timeout) {
		By newLocator = locParser(locator,replacement);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(newLocator));
		return element;
	}
	
	public boolean waitForUrl(String url, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.urlContains(url));
	}

	public Alert waitForAlertToBePresent(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		return alert;
	}
	
	public String handleAlert() {
		waitForAlertToBePresent(5);
		String alertBoxText = null;
		try {
			Alert alertBox = driver.switchTo().alert();
			alertBoxText = alertBox.getText();
			alertBox.accept();
			System.out.println("Alert handled!");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("No Alert window appeared...");
			System.out.println(e.getMessage());
		}
		return alertBoxText;
	}

	// clickWhenReady:
	public void clickWhenReady(By locator, int timeout) {
		try{ 
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			System.out.println("Locator: " + locator);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			if (getProperty("highlight").equalsIgnoreCase("yes")) {
				jsUtil.flash(element);
			}
			element.click();
		}catch(TimeoutException e) {
			System.out.println("TIMEOUT ERROR!!! \nDriver failed to find element. Either check locator or increase time limit.");
		}
	}

	public void clickWhenReady(By locator, String replacement, int timeout) {
		try{ 
			By newLocator = locParser(locator,replacement);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(newLocator));
			if (getProperty("highlight").equalsIgnoreCase("yes")) {
				jsUtil.flash(element);
			}
			element.click();
		}catch(TimeoutException e) {
			throw new TimeoutException("\nTIMEOUT ERROR!!! \nDriver failed to find element. Either check locator or increase time limit.");
		}
	}
	
	public Boolean waitForTitleToBePresent(String title, int timeout) {
		try {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		if(wait.until(ExpectedConditions.titleContains(title)))
			return true;
		}
		catch(TimeoutException time) {
			return false;
		}
		return false;
	}
}