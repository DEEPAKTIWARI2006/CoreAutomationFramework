/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;
import java.io.IOException;
import java.util.HashMap;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;

/**
 * This instantiates the FireFoxDriver
 */
/**
 * @author deepaktiwari
 *
 */
public class FirefoxDriverManager {

  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(FirefoxDriverManager.class);
  /**
   * 
   */
  private static WebDriver driver;
  /**
   * 
   */
  private static FirefoxOptions options;

  /**
   * @param paramMap
   * @return
   * @throws IOException 
   */
  public static WebDriver createDriver(HashMap<String, String> paramMap) throws IOException {
    logger.info("Starting FirefoxDriver" + "  --  " + Thread.currentThread().getId());
    String OS = System.getProperty("os.name").toLowerCase();
    if (OS.contains("mac"))
      System.setProperty("webdriver.gecko.driver",
          System.getProperty("user.dir") + "/src/main/resources/Drivers/geckodriver");
    else if (OS.contains("windows"))
      System.setProperty("webdriver.gecko.driver",
          System.getProperty("user.dir") + "/src/main/resources/Drivers/geckodriver.exe");

    driver = new FirefoxDriver(getOptions());
    return driver;
  }

  /**
   * @return
   * @throws IOException 
   */
  private static FirefoxOptions getOptions() throws IOException {

    PropertyFileReaderImpl prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    logger.info("Setting Firefox Options");
    options = new FirefoxOptions();
     options.addArguments("test-type");
    
    if (prop.GetProperty("DisableExtensions").equalsIgnoreCase("true"))
      options.addArguments("--disable-extensions");

    if (prop.GetProperty("DisableInfobars").equalsIgnoreCase("true"))
      options.addArguments("disable-infobars");

    if (prop.GetProperty("SetAcceptInsecureCerts").equalsIgnoreCase("true"))
      options.setAcceptInsecureCerts(true);

    if (prop.GetProperty("SetUnhandledPromptBehaviour").equalsIgnoreCase("ACCEPT"))
      options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

    if (prop.GetProperty("StartMaximized").equalsIgnoreCase("true"))
      options.addArguments("--start-maximized");
    return options;

  }

}
