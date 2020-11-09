/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.frameworkutils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a utility class which provides common user actions This class
 * contains user actions from Selenium, Javascript and Actions libraries
 */
public class UserActions {

	private static WebDriver driver = null;
	private JavascriptExecutor js = null;
	private WebDriverWait wait;
	private static Logger logger = LoggerFactory.getLogger(UserActions.class);

	/**
	 * The below contructor gets the driver instance and instantiates the Javascript
	 * executor object
	 */

	public UserActions(WebDriver driver) {
		UserActions.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public void SendKeysUsingActions(WebElement element, String Text) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).click().sendKeys(element, Text).perform();
	}

	public void TypeTextUsingRobot(String Text) throws AWTException {
		Robot r = new Robot();
		for (int i = 0; i < Text.length(); i++) {
			char res = Text.charAt(i);
			r.keyPress(res);
			r.keyRelease(res);
			r.delay(2000);
		}
	}

	public void ConfirmAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public void DismissAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public String GetAlertText() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	public void SendTextToAlert(String text) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(text);
	}

	public void SetText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	public void ClickElementJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public void ClickElementJS(String baseXpath, String replaceString) {
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString))));
	}

	public void ScrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void SendTextJS(WebElement element, String text) {
		String jsscript = "arguments[0].value='" + text + "';";
		// jsscript = "document.getElementById('budget').value='1000'";
		js.executeScript(jsscript, element);
	}
	
	public void SendTextJSByTagName(String element, String text) {
		js.executeScript("document.getElementsByTagName" + element + ".value='" + text + "'");;
	}

	public void ScrollPageByPixelsJS(int xCoordinate, int yCoordinate) {
		js.executeScript("window.scrollBy(" + xCoordinate + "," + yCoordinate + ")");
	}

	public void WaitAndClick(String xPath, int timeInSecs) {
		wait = new WebDriverWait(driver, timeInSecs);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
		element.click();
	}

	public void WaitAndClick(WebElement element, int timeInSecs) {
		wait = new WebDriverWait(driver, timeInSecs);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void WaitUntilVisible(WebElement element, int timeInSecs) {
		wait = new WebDriverWait(driver, timeInSecs);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void WaitUntilInvisible(WebElement element, int timeInSecs) {
		wait = new WebDriverWait(driver, timeInSecs);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void WaitUntilInvisible(String baseXpath, String replaceString, int timeInSecs) {
		WebElement element;

		if (null != replaceString)
			element = driver
					.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
		else
			element = driver.findElement(By.xpath(baseXpath));

		if (null != replaceString) {
			wait = new WebDriverWait(driver, timeInSecs);
			wait.until(ExpectedConditions.invisibilityOf(element));
		}
	}

	public void WaitUntilInvisible(String baseXpath, int timeInSecs) {
		WaitUntilInvisible(baseXpath, null, timeInSecs);
	}

	public void WaitUntilInvisibilityOfElementLocated(By by, int timeInSecs) {
		try {
			wait = new WebDriverWait(driver, timeInSecs);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			do {
				GenericFunctions.WaitForSeconds(5);
			} while (driver.findElements(by).size() > 0);
		}
	}


	public void SendText(WebElement element, String text) {
		if (null != text) {
			element.clear();
			element.sendKeys(text);
			GenericFunctions.LogAndReportInfo("Setting Text -- " + text);
		}
	}

	public void SendText(String elementXpath, String text) {
		if (null != text) {
			SendText(elementXpath, null, text);
		} else {
			GenericFunctions.LogAndReportError("text sent is null");
		}
	}

	public void SendText(String elementXpath, String text, int elementIndex) {
		if (null != text) {
			SendText(elementXpath, null, text, elementIndex);
		} else {
			GenericFunctions.LogAndReportError("text sent is null");
		}
	}
	
	public void ClearAndSendText(String baseXpath, String replaceString, String text) {
		WebElement element;

		if (null != replaceString)
			element = driver
					.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
		else
			element = driver.findElement(By.xpath(baseXpath));
		
		element.click();
		int strLen = element.getAttribute("value").length();
		for(int i = 0; i < strLen; i++){
			element.sendKeys(Keys.BACK_SPACE);
		}
		element.sendKeys(text);
	}
	
	public void ClearAndSendText(WebElement element, String text) {
		element.click();
		int strLen = element.getAttribute("value").length();
		for(int i = 0; i < strLen; i++){
			element.sendKeys(Keys.BACK_SPACE);
		}
		element.sendKeys(text);
	}
	
	public void ClearAndSendText(String baseXpath, String text) {

		if (null != text)
			ClearAndSendText(baseXpath, null, text);
		else {
			GenericFunctions.LogAndReportError("text sent is null");
		}
	}

	public void SendText(String baseXpath, String replaceString, String text, int elementIndex) {
		WebElement element;

		if (null != replaceString)
			element = driver
					.findElements(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
					.get(elementIndex);
		else
			element = driver.findElements(By.xpath(baseXpath)).get(elementIndex);

		if (null != text) {
			element.clear();
			element.sendKeys(text);
			GenericFunctions.LogAndReportInfo("Setting Text -- " + text);
		}
	}

	public void SendText(String baseXpath, String replaceString, String text) {
		WebElement element;

		if (null != replaceString)
			element = driver
					.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
		else
			element = driver.findElement(By.xpath(baseXpath));

		if (null != text) {
			element.clear();
			element.sendKeys(text);
			GenericFunctions.LogAndReportInfo("Setting Text -- " + text);
		}
	}

	public void ClickElement(String elementXpath) {
		ClickElement(elementXpath, null);
	}

	public void ClickElement(String baseXpath, String replaceString) {
		driver.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString))).click();
	}

	public void ClickNthElement(String baseXpath, String replaceString, int elementIndex) {
		driver.findElements(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
				.get(elementIndex).click();
	}

	public void ClickElement(WebElement element) {
		element.click();
	}

	public boolean FindElementFromXpath(String elementXpath) {
		return FindElementFromXpath(elementXpath, null);
	}

	public boolean FindElementFromXpath(String baseXpath, String replaceString) {
		try {
			driver.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isClickable(WebElement element, int timeInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean IsElementDisplayed(WebElement element) {

		try {
			if (element.isDisplayed()) {
				logger.info(element.toString() + "  Element is Present and displayed");
				return true;
			} else {
				logger.info(element.toString() + "  Element is Present but hidden");
				return false;
			}
		} catch (Exception e) {
			logger.info("Element is NOT Present");
			return false;
		}
	}

	public boolean IsElementDisplayed(String elementXpath) {
		return IsElementDisplayed(elementXpath, null);
	}

	public boolean IsElementDisplayed(String baseXpath, String replaceString) {
		try {
			if (driver.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
					.isDisplayed()) {
				logger.info(driver
						.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
						.toString() + "  Element is Present and displayed");
				return true;
			} else {
				logger.info(driver
						.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
						.toString() + "  Element is Present but hidden");
				return false;
			}
		} catch (Exception e) {
			logger.info("Element is NOT Present");
			return false;
		}
	}

	public WebElement GetElement(String baseXpath, String replaceString) {
		return driver.findElement(By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
	}

	public WebElement GetElement(String xPath) {
		return driver.findElement(By.xpath(xPath));
	}
	
	public boolean isClickable(WebElement element)      
	{
	try
	{
	   WebDriverWait wait = new WebDriverWait(driver, 5);
	   wait.until(ExpectedConditions.elementToBeClickable(element));
	   return true;
	}
	catch (Exception e)
	{
	  return false;
	}
	
	}
	
	public void highLightElement(WebDriver driver, WebElement element)
	{
	JavascriptExecutor js=(JavascriptExecutor)driver; 

	js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

	try 
	{
	Thread.sleep(1000);
	} 
	catch (InterruptedException e) {

	System.out.println(e.getMessage());
	} 

	js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element); 

	}

	
	
}
