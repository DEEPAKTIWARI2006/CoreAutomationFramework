/**
 * @author dtiwa1
 *
 */

package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AndroidDriverManager {

  /**
   * 
   */
  private static WebDriver driver;
  /**
   * 
   */
  private static AppiumDriverLocalService driverLocalService;
  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(AndroidDriverManager.class);
  /**
   * 
   */
  private static PropertyFileReaderImpl prop;

  /**
   * @param paramMap
   * @return
   * @throws IOException
   */

  public static WebDriver createDriver(HashMap<String, String> paramMap) throws IOException {

    prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    // driverLocalService = startAppiumServer(paramMap);
    // driver = new AndroidDriver<MobileElement>(driverLocalService, getCapabilities(paramMap));
    // driver.get("https://www.guru99.com/introduction-to-appium.html");
    driver = new AndroidDriver<MobileElement>(getCapabilities(paramMap));
    return driver;
  }

  /**
   * @param paramMap
   * @return
   * @throws IOException
   */

  private static DesiredCapabilities getCapabilities(HashMap<String, String> paramMap)
      throws IOException {

    final DesiredCapabilities capabilities = DesiredCapabilities.android();
    // capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("automationName", "uiautomator2");
    capabilities.setCapability("newCommandTimeout", 60);

    if (paramMap.get("BrowserName").equalsIgnoreCase("Chrome")) {
      capabilities.setBrowserName("Chrome");
      capabilities.setCapability("deviceName", paramMap.get("DeviceName"));
    } else if (paramMap.get("BrowserName").equalsIgnoreCase("App")) {
      capabilities.setCapability("app", paramMap.get("AppPath"));
      // capabilities.setCapability("platformVersion", paramMap.get("PlatformVersion"));

      if (prop.GetProperty("AndroidfullReset").equalsIgnoreCase("true"))
        capabilities.setCapability("fullReset", true);

      if (prop.GetProperty("AndroidunicodeKeyboard").equalsIgnoreCase("true"))
        capabilities.setCapability("unicodeKeyboard", true);

      if (prop.GetProperty("AndroidresetKeyboard").equalsIgnoreCase("true"))
        capabilities.setCapability("resetKeyboard", true);

      if (prop.GetProperty("AndroidnoReset").equalsIgnoreCase("true"))
        capabilities.setCapability("noReset", true);
    }

    return capabilities;
  }

  /**
   * @param paramMap
   * @return
   */

  public static AppiumDriverLocalService startAppiumServer(HashMap<String, String> paramMap) {

    driverLocalService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
        .withLogFile(
            new File(System.getProperty("user.dir") + prop.GetProperty("AppiumLogPath").toString()))
        .withIPAddress(prop.GetProperty("IPAddress").toString())
        .usingPort(Integer.parseInt(prop.GetProperty("Port")))
        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
        .withArgument(GeneralServerFlag.LOG_LEVEL, prop.GetProperty("LogLevel").toString())
        .withArgument(GeneralServerFlag.LOG_TIMESTAMP));

    driverLocalService.start();
    return driverLocalService;
  }

  /**
   * 
   */

  @AfterTest
  public static void stopServer() {

    try {
      driver.quit();
      driverLocalService.stop();
    } catch (Exception e) {
      logger.error(e.getMessage().toString());
    }
  }
}
