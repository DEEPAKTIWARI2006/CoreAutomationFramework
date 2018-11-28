/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;
import frameworkcore.reporting.DriverEventListenerImpl;

/**
 * This class manages the instantiation of the drivers based on the value provided by the TestNG.xml
 */
/**
 * @author deepaktiwari
 *
 */
public class DriverManager {

  /**
   * 
   */
  private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
  /**
   * 
   */
  private static ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();

  private static long waitTime;
  private static String browserName;

  /**
   * @param ParamMap
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws IOException
   */
  public static void setDriver(HashMap<String, String> ParamMap)
      throws InvocationTargetException, IllegalAccessException, IOException {

    browserName = ParamMap.get("BrowserName");
    String platformName = ParamMap.get("PlatformName");

    logger.info("Setting driver type " + browserName + " and instantiating driver"
        + Thread.currentThread().getId());

    switch (browserName) {
      case CHROME:
        if (platformName.equalsIgnoreCase("Windows") || platformName.equalsIgnoreCase("Mac")
            || platformName.equalsIgnoreCase("Responsive"))
          setWebDriver(ChromeDriverManager.createDriver(ParamMap));
        else if (platformName.equalsIgnoreCase("Android"))
          setWebDriver(AndroidDriverManager.createDriver(ParamMap));
        else if (platformName.equalsIgnoreCase("Mac"))
          setWebDriver(iOSDriverManager.createDriver(ParamMap));
        break;

      case FIREFOX:
        setWebDriver(FirefoxDriverManager.createDriver(ParamMap));
        break;

      case IE:
        setWebDriver(IEDriverManager.createDriver(ParamMap));
        break;

      case SAFARI:
        if (platformName.equalsIgnoreCase("Mac"))
          setWebDriver(SafariDriverManager.createDriver(ParamMap));
        else if (platformName.equalsIgnoreCase("iOS"))
          setWebDriver(iOSDriverManager.createDriver(ParamMap));
        break;

      case APP:
        if (platformName.equalsIgnoreCase("Android"))
          setWebDriver(AndroidDriverManager.createDriver(ParamMap));
        else if (platformName.equalsIgnoreCase("Mac"))
          setWebDriver(iOSDriverManager.createDriver(ParamMap));
        break;

      case BROWSERSTACK:
        if (platformName.equalsIgnoreCase("Android"))
          setWebDriver(BrowserStackManager.createDriver(ParamMap));
        break;
    }
  }

  /**
   * @return
   */
  public static WebDriver getDriver() {
    logger.info("Getting webdriver instance from thread " + Thread.currentThread().getId());
    return webdriver.get();
  }

  public static void CloseAndQuitDriver() {
    try {
      logger.info("Closing and quiting driver " + getDriver().toString());
      WebDriver driver = getDriver();
      driver.close();
      driver.quit();
    } catch (Exception e) {
      logger.error("Not able to quit driver. Please check the logs");
      logger.error(e.getStackTrace().toString());
      // killDriverProcess();
    }finally {
      webdriver.remove();
    }
  }
  
  
  public static String getDriverType(WebDriver driver) {
    if (driver instanceof FirefoxDriver) {
      logger.info("Driver type is Firefox");
      return "FirefoxDriver";
    } else if (driver instanceof ChromeDriver) {
      logger.info("Driver type is Chrome");
      return "ChromeDriver";
    } else if (driver instanceof SafariDriver) {
      logger.info("Driver type is Safari");
      return "SafariDriver";
    } else if (driver instanceof InternetExplorerDriver) {
      logger.info("Driver type is IE");
      return "IEDriver";
    }else {
      return "Driver Type is not determined";
    }
  }

  /**
   * @param driver
   * @throws IOException
   */
  static void setWebDriver(WebDriver driver) throws IOException {
    EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
    DriverEventListenerImpl eventlistener = new DriverEventListenerImpl();
    eventDriver.register(eventlistener);
    setDriverConfiguration(eventDriver);
    logger.info("Setting webdriver instance to thread " + Thread.currentThread().getId());
    webdriver.set(eventDriver);
  }

  /**
   * @param driver
   * @throws IOException
   */
  private static void setDriverConfiguration(WebDriver driver) throws IOException {
    PropertyFileReaderImpl prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    waitTime = Long.parseLong(prop.GetProperty("implicitWait"));
    driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
    if (browserName.equalsIgnoreCase("Safari"))
      driver.manage().window().maximize();
    else
      driver.manage().window().fullscreen();

    driver.manage().deleteAllCookies();
  }

  public static void setDefaultImplicitWait(WebDriver driver) {
    driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
  }
}
