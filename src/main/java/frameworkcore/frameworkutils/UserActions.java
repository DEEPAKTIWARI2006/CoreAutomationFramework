/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.frameworkutils;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * This is a utility class which provides common user actions This class contains user actions from
 * Selenium, Javascript and Actions libraries
 */
/**
 * @author deepaktiwari
 *
 */
public class UserActions {

  /**
   * 
   */
  private static WebDriver driver = null;
  /**
   * 
   */
  private JavascriptExecutor js = null;
  /**
   * 
   */
  private WebDriverWait wait;
  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(UserActions.class);

  /**
   * The below contructor gets the driver instance and instantiates the Javascript executor object
   */

  /**
   * @param driver
   */
  public UserActions(WebDriver driver) {
    UserActions.driver = driver;
    js = (JavascriptExecutor) driver;
  }

  /**
   * @param element
   * @param Text
   */
  public void SendKeysUsingActions(WebElement element, String text) {
    Actions builder = new Actions(driver);
    builder.moveToElement(element).click().sendKeys(element, text).perform();
  }

  /**
   * @param Text
   * @throws AWTException
   */
  public void TypeTextUsingRobot(String Text) throws AWTException {
    Robot r = new Robot();
    for (int i = 0; i < Text.length(); i++) {
      char res = Text.charAt(i);
      r.keyPress(res);
      r.keyRelease(res);
      r.delay(2000);
    }
  }

  /**
   * 
   */
  public void ConfirmAlert() {
    Alert alert = driver.switchTo().alert();
    alert.accept();
  }

  /**
   * 
   */
  public void DismissAlert() {
    Alert alert = driver.switchTo().alert();
    alert.dismiss();
  }

  /**
   * @return
   */
  public String GetAlertText() {
    Alert alert = driver.switchTo().alert();
    return alert.getText();
  }

  /**
   * @param text
   */
  public void SendTextToAlert(String text) {
    Alert alert = driver.switchTo().alert();
    alert.sendKeys(text);
  }
  
  
  public void switchToParentTab() {
    Set <String> st= driver.getWindowHandles();
    Iterator<String> it = st.iterator();
    String parent =  it.next();
    String child =it.next();
    driver.switchTo().window(parent);
    GenericFunctions.WaitForSeconds(1);
  }
  
  
  public void switchToChildTab() {
    Set <String> st= driver.getWindowHandles();
    Iterator<String> it = st.iterator();
    String parent =  it.next();
    String child =it.next();
    driver.switchTo().window(child);
    GenericFunctions.WaitForSeconds(1);
  }

  /**
   * @param element
   * @param text
   */
  public void SetText(WebElement element, String text) {
    element.clear();
    element.sendKeys(text);
  }

  /**
   * @param element
   */
  public void ClickElementJS(WebElement element) {
    js.executeScript("arguments[0].click();", element);
  }


  public void ClickElementJS(String baseXpath, String replaceString) {
    ClickElementJS(GetElement(baseXpath, replaceString));
  }

  /**
   * @param element
   */
  public void ScrollIntoView(WebElement element) {
    js.executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /**
   * @param element
   * @param text
   */
  public void SendTextJS(WebElement element, String text) {
    String jsscript = "arguments[0].value='" + text + "';";
    // jsscript = "document.getElementById('budget').value='1000'";
    js.executeScript(jsscript, element);
  }

  /**
   * @param element
   * @param text
   */
  public void SendTextJSByTagName(String element, String text) {
    js.executeScript("document.getElementsByTagName" + element + ".value='" + text + "'");
  }

  /**
   * @param xCoordinate
   * @param yCoordinate
   */
  public void ScrollPageByPixelsJS(int xCoordinate, int yCoordinate) {
    js.executeScript("window.scrollTo(" + xCoordinate + "," + yCoordinate + ")");
  }
  
  /**
   * 
   */
  public void scrollPageToTop() {
    js.executeScript("window.scrollTo(" + 0 + "," + 0 + ")");
  }

  /**
   * @param element
   */
  public void MouseHover(WebElement element) {
    String strJavaScript =
        "var element = arguments[0];" + "var mouseEventObj = document.createEvent('MouseEvents');"
            + "mouseEventObj.initEvent( 'mouseover', true, true );"
            + "element.dispatchEvent(mouseEventObj);";

    js.executeScript(strJavaScript, element);
  }


  public void HoverOnElement(WebElement element) {
    new Actions(driver).moveToElement(element).build().perform();
  }

  public void HoverNClick(WebElement element) {
    new Actions(driver).moveToElement(element).click().build().perform();
  }

  /**
   * @param xPath
   * @param timeInSecs
   */
  public void WaitAndClick(String xPath, int timeInSecs) {
    wait = new WebDriverWait(driver, timeInSecs);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
    element.click();
  }

  /**
   * @param element
   * @param timeInSecs
   */
  public void WaitAndClick(WebElement element, int timeInSecs) {
    wait = new WebDriverWait(driver, timeInSecs);
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.click();
  }

  /**
   * @param element
   * @param timeInSecs
   */
  public void WaitUntilVisible(By by, int timeInSecs) {
    wait = new WebDriverWait(driver, timeInSecs);
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param timeInSecs
   */
  public void WaitUntilVisible(String baseXpath, String replaceString, int timeInSecs) {
    By by;

    if (replaceString != null)
      by = By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString));
    else
      by = By.xpath(baseXpath);

    if (null != replaceString) {
      WaitUntilVisible(by, timeInSecs);
    }
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param timeInSecs
   */
  public void WaitUntilVisible(String baseXpath, int timeInSecs) {
    WaitUntilVisible(baseXpath, null, timeInSecs);
  }

  /**
   * @param element
   * @param timeInSecs
   */
  public void WaitUntilInvisible(WebElement element, int timeInSecs) {
    wait = new WebDriverWait(driver, timeInSecs);
    wait.until(ExpectedConditions.invisibilityOf(element));
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param timeInSecs
   */
  public void WaitUntilInvisible(String baseXpath, String replaceString, int timeInSecs) {
    WebElement element;

    if (replaceString != null)
      element = driver.findElement(
          By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
    else
      element = driver.findElement(By.xpath(baseXpath));

    if (null != replaceString) {
      wait = new WebDriverWait(driver, timeInSecs);
      wait.until(ExpectedConditions.invisibilityOf(element));
    }
  }

  /**
   * @param baseXpath
   * @param timeInSecs
   */
  public void WaitUntilInvisible(String baseXpath, int timeInSecs) {
    WaitUntilInvisible(baseXpath, null, timeInSecs);
  }

  /**
   * @param by
   * @param timeInSecs
   */
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

  /**
   * @param by
   * @param timeInSecs
   */
  public void WaitUntilPresenseOfElementLocated(By by, int timeInSecs) {
    try {
      wait = new WebDriverWait(driver, timeInSecs);
      wait.until(ExpectedConditions.presenceOfElementLocated(by));
    } catch (Exception e) {
      do {
        GenericFunctions.WaitForSeconds(5);
      } while (driver.findElements(by).size() > 0);
    }
  }

  /**
   * @param by
   * @param timeInSecs
   */
  public void WaitUntilPresenseOfElementLocated(String baseXpath, String replaceString,
      int timeInSecs) {
    By by = null;
    if (replaceString != null)
      by = By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString));
    else
      by = By.xpath(baseXpath);

    try {
      wait = new WebDriverWait(driver, timeInSecs);
      wait.until(ExpectedConditions.presenceOfElementLocated(by));
    } catch (Exception e) {
      do {
        GenericFunctions.WaitForSeconds(5);
      } while (driver.findElements(by).size() > 0);
    }
  }

  /**
   * @param by
   * @param timeInSecs
   */
  public void WaitUntilPresenseOfElementLocated(String baseXpath, int timeInSecs) {
    WaitUntilPresenseOfElementLocated(baseXpath, null, timeInSecs);
  }

  /**
   * @param element
   * @param text
   */
  public void SendText(WebElement element, String text) {
    if (null != text) {
      element.clear();
      element.sendKeys(text);
      GenericFunctions.LogAndReportInfo("Setting Text -- " + text);
    }
  }

  /**
   * @param elementXpath
   * @param text
   */
  public void SendText(String elementXpath, String text) {
    if (null != text) {
      SendText(elementXpath, null, text);
    } else {
      GenericFunctions.LogAndReportError("text sent is null");
    }
  }

  /**
   * @param elementXpath
   * @param text
   * @param elementIndex
   */
  public void SendText(String elementXpath, String text, int elementIndex) {
    if (text != null) {
      SendText(elementXpath, null, text, elementIndex);
    } else {
      GenericFunctions.LogAndReportError("text sent is null");
    }
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param textor
   */
  public void ClearAndSendText(String baseXpath, String replaceString, String text) {
    WebElement element;

    if (replaceString != null)
      element = driver.findElement(
          By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
    else
      element = driver.findElement(By.xpath(baseXpath));

    element.click();
    int strLen = element.getAttribute("value").length();
    for (int i = 0; i < strLen; i++) {
      element.sendKeys(Keys.BACK_SPACE);
    }

    GenericFunctions.WaitForSeconds(1);
    element.sendKeys(text);
  }

  public void ClearAndSendTextJS(String baseXpath, String replaceString, String text) {
    WebElement element;
    if (replaceString != null)
      element = driver.findElement(
          By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
    else
      element = driver.findElement(By.xpath(baseXpath));
    js.executeScript("arguments[0].value = '';", element);
    SendTextJS(element, text);
  }

  /**
   * @param element
   * @param text
   */
  public void ClearAndSendText(WebElement element, String text) {
    element.click();
    int strLen = element.getAttribute("value").length();
    for (int i = 0; i < strLen; i++) {
      element.sendKeys(Keys.BACK_SPACE);
    }
    element.sendKeys(text);
  }

  /**
   * @param baseXpath
   * @param text
   */
  public void ClearAndSendText(String baseXpath, String text) {

    if (text != null)
      ClearAndSendText(baseXpath, null, text);
    else {
      GenericFunctions.LogAndReportError("text sent is null");
    }
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param text
   * @param elementIndex
   */
  public void SendText(String baseXpath, String replaceString, String text, int elementIndex) {
    WebElement element;

    if (replaceString != null)
      element = driver
          .findElements(
              By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
          .get(elementIndex);
    else
      element = driver.findElements(By.xpath(baseXpath)).get(elementIndex);

    if (null != text) {
      element.clear();
      element.sendKeys(text);
      GenericFunctions.LogAndReportInfo("Setting Text -- " + text);
    }
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param text
   */
  public void SendText(String baseXpath, String replaceString, String text) {
    WebElement element;

    if (replaceString != null)
      element = driver.findElement(
          By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
    else
      element = driver.findElement(By.xpath(baseXpath));

    if (null != text) {
      element.clear();
      element.sendKeys(text);
      GenericFunctions.LogAndReportInfo("Setting Text -- " + text);
    }
  }

  public String GetAttributeOfElement(WebElement element, String AttributeName) {
    return element.getAttribute(AttributeName);
  }

  /**
   * @param elementXpath
   */
  public void ClickElement(String elementXpath) {
    ClickElement(elementXpath, null);
  }

  /**
   * @param baseXpath
   * @param replaceString
   */
  public void ClickElement(String baseXpath, String replaceString) {
    driver
        .findElement(
            By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
        .click();
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @param elementIndex
   */
  public void ClickNthElement(String baseXpath, String replaceString, int elementIndex) {
    driver
        .findElements(
            By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
        .get(elementIndex).click();
  }

  /**
   * @param element
   */
  public void ClickElement(WebElement element) {
    element.click();
  }

  /**
   * @param elementXpath
   * @return
   */
  public boolean FindElementFromXpath(String elementXpath) {
    return FindElementFromXpath(elementXpath, null);
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @return
   */
  public boolean FindElementFromXpath(String baseXpath, String replaceString) {
    try {
      driver.findElement(
          By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @param element
   * @param timeInSeconds
   * @return
   */
  public boolean isClickable(WebElement element, int timeInSeconds) {
    try {
      WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
      wait.until(ExpectedConditions.elementToBeClickable(element));
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  /**
   * @param element
   * @return
   */
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

  /**
   * @param elementXpath
   * @return
   */
  public boolean IsElementDisplayed(String elementXpath) {
    return IsElementDisplayed(elementXpath, null);
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @return
   */
  public boolean IsElementDisplayed(String baseXpath, String replaceString) {
    try {
      if (driver
          .findElement(
              By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
          .isDisplayed()) {
        logger.info(driver
            .findElement(
                By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
            .toString() + "  Element is Present and displayed");;
        return true;
      } else {
        logger.info(driver
            .findElement(
                By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)))
            .toString() + "  Element is Present but hidden");
        return false;
      }
    } catch (Exception e) {
      logger.info("Element is NOT Present");
      return false;
    }
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @return
   */
  public WebElement GetElement(String baseXpath, String replaceString) {
    return driver.findElement(
        By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
  }

  /**
   * @param baseXpath
   * @param replaceString
   * @return
   */
  public List<WebElement> GetElements(String baseXpath, String replaceString) {
    return driver.findElements(
        By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
  }

  /**
   * @param xPath
   * @return
   */
  public List<WebElement> GetElements(String xPath) {
    return driver.findElements(By.xpath(xPath));
  }

  /**
   * @param xPath
   * @return
   */
  public WebElement GetElement(String xPath) {
    return driver.findElement(By.xpath(xPath));
  }

  /**
   * @param element
   * @return
   */
  public boolean isClickable(WebElement element) {
    try {
      WebDriverWait wait = new WebDriverWait(driver, 5);
      wait.until(ExpectedConditions.elementToBeClickable(element));
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  /**
   * @param driver
   * @param element
   */
  public void highLightElement(WebDriver driver, WebElement element) {

    js.executeScript(
        "arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
        element);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {

      System.out.println(e.getMessage());
    }

    js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

  }

  public String getElementText(WebElement element) {

    String elementText = null;
    try {
      elementText = element.getText();
    } catch (Exception e) {
      logger.info("Unable to fetch element text, trying with JS executor", e);
      elementText = (String) js.executeScript("“return arguments[0].text;”", element);
    } finally {
      return elementText;
    }
  }

  public void ClearFieldJS(WebElement element) {
    js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].value = '';", element);
  }


  public void waitForPageLoaded() {
    ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver driver) {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
            .equals("complete");
      }
    };
    try {
      Thread.sleep(1000);
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(expectation);
    } catch (Throwable error) {
      Assert.fail("Timeout waiting for Page Load Request to complete.");
    }
  }


  public boolean waitForJSandJQueryToLoad() {
    WebDriverWait wait = new WebDriverWait(driver, 30);
    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() { // this will wait for
                                                                               // jQuery to load
      @Override
      public Boolean apply(WebDriver driver) {
        try {
          return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
        } catch (Exception e) { // if no jQuery is present

          return true;
        }
      }
    };
    // waiting for javascript to load
    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
            .equals("complete");
      }
    };
    return wait.until(jQueryLoad) && wait.until(jsLoad);
  }

  public boolean refreshJS() {
    boolean executedActionStatus = false;
    try {
      ((JavascriptExecutor) driver).executeScript("document.location.reload()");
      Thread.sleep(2000);
      executedActionStatus = true;
    } catch (Exception er) {
      er.printStackTrace();
    }
    return executedActionStatus;
  }

  public void SendTextByCharacter(WebElement element, String value) {
    String val = value;
    element.clear();
    for (int i = 0; i < val.length(); i++) {
      char c = val.charAt(i);
      String s = new StringBuilder().append(c).toString();
      element.sendKeys(s);
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        // Do nothing
      }
    }
  }
  
  public void SendTextByCharacter(String baseXpath, String replaceString, String value) {
    String val = value;
    WebElement element = null;
    if(replaceString==null) {
     element = driver.findElement(
        By.xpath(baseXpath));
    }else {
       element = driver.findElement(
          By.xpath(GenericFunctions.GetElementWithDynamicXPath(baseXpath, replaceString)));
    }
    element.clear();
    for (int i = 0; i < val.length(); i++) {
      char c = val.charAt(i);
      String s = new StringBuilder().append(c).toString();
      element.sendKeys(s);
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        // Do nothing
      }
    }
  }
  
  public void SendTextByCharacter(String baseXpath, String value) {
    SendTextByCharacter(baseXpath, null, value);
  }
}
