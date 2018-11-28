/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;

/**
 * This instantiates the ChromeDriver
 */
/**
 * @author deepaktiwari
 *
 */
class ChromeDriverManager {

  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(ChromeDriverManager.class);
  /**
   * 
   */
  private static WebDriver driver = null;
  /**
   * 
   */
  private static ChromeOptions options;
  private static HashMap<String, String> parameterMap;

  /**
   * @param paramMap
   * @return
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws IOException
   */
  static WebDriver createDriver(HashMap<String, String> paramMap)
      throws InvocationTargetException, IllegalAccessException, IOException {
    parameterMap = paramMap;
    String OS = System.getProperty("os.name").toLowerCase();
    logger.info("Starting ChromeDriver");

    if (OS.contains("mac"))
      System.setProperty("webdriver.chrome.driver",
          System.getProperty("user.dir") + "/src/main/resources/Drivers/chromedriver");
    else if (OS.contains("windows"))
      System.setProperty("webdriver.chrome.driver",
          System.getProperty("user.dir") + "/src/main/resources/Drivers/chromedriver.exe");

    driver = new ChromeDriver(getOptions());
    return driver;

  }

  /**
   * @author deepaktiwari
   * @throws IOException
   *
   */

  private static ChromeOptions getOptions() throws IOException {

    PropertyFileReaderImpl prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);

    logger.info("Setting Chrome Options");
    options = new ChromeOptions();

    if (prop.GetProperty("DisableExtensions").equalsIgnoreCase("true"))
      options.addArguments("--disable-extensions");

    if (prop.GetProperty("DisableInfobars").equalsIgnoreCase("true"))
      options.addArguments("disable-infobars");

    if (prop.GetProperty("SetAcceptInsecureCerts").equalsIgnoreCase("true"))
      options.setAcceptInsecureCerts(true);

    if (prop.GetProperty("SetUnhandledPromptBehaviour").equalsIgnoreCase("ACCEPT"))
      options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

    if (prop.GetProperty("ChromeSetHeadless").equalsIgnoreCase("true"))
      options.setHeadless(true);
    else
      options.setHeadless(false);

    if (prop.GetProperty("StartMaximized").equalsIgnoreCase("true"))
      options.addArguments("--start-maximized");

    if (parameterMap.get("PlatformName").equalsIgnoreCase("Responsive")) {
      Map<String, String> mobileEmulation = new HashMap<String, String>();
      mobileEmulation.put("deviceName", parameterMap.get("DeviceName"));
      options.setExperimentalOption("mobileEmulation", mobileEmulation);
    }

    // DesiredCapabilities ChromeCapabilities = DesiredCapabilities.chrome();
    // ChromeCapabilities.setPlatform(Platform.WINDOWS);

    // options.merge(ChromeCapabilities);
    return options;

  }

}
