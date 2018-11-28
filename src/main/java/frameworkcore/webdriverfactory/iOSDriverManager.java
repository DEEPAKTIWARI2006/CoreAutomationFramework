/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
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
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * @author deepaktiwari
 *
 */
public class iOSDriverManager {

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
    driver = new IOSDriver<MobileElement>(getCapabilities(paramMap));
    return driver;
  }

  /**
   * @param paramMap
   * @return
   * @throws IOException
   */
  private static DesiredCapabilities getCapabilities(HashMap<String, String> paramMap)
      throws IOException {

    PropertyFileReaderImpl prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    DesiredCapabilities capabilities = DesiredCapabilities.iphone();
    capabilities.setPlatform(Platform.IOS);
    capabilities.setCapability("automationName", "XCUITest");

    if (paramMap.get("BrowserName").equalsIgnoreCase("Safari")) {
      capabilities.setBrowserName("Safari");
      capabilities.setCapability("deviceName", paramMap.get("DeviceName"));
    }
    
    capabilities.setCapability("udid", prop.GetProperty("udid"));
    if (paramMap.get("BrowserName").equalsIgnoreCase("App")) {
      capabilities.setCapability("app", paramMap.get("AppPath"));  
      if (prop.GetProperty("IosfullReset").equalsIgnoreCase("true"))
        capabilities.setCapability("fullReset", true);

      if (prop.GetProperty("IosnoReset").equalsIgnoreCase("true"))
        capabilities.setCapability("noReset", true);

      capabilities.setCapability("xcodeOrgId", prop.GetProperty("xcodeOrgId"));
      capabilities.setCapability("xcodeSigningId", prop.GetProperty("xcodeSigningId"));
      capabilities.setCapability("agentPath", prop.GetProperty("agentPath"));
      capabilities.setCapability("bootstrapPath", prop.GetProperty("bootstrapPath"));
    }
    // capabilities.setCapability("platformVersion", paramMap.get("PlatformVersion"));
      

      


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
        .withIPAddress(prop.GetProperty("IPAddress").toString()).usingAnyFreePort()
        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
        .withArgument(GeneralServerFlag.LOG_LEVEL, prop.GetProperty("LogLevel").toString())
        .withArgument(GeneralServerFlag.LOG_TIMESTAMP));

    driverLocalService.start();
    return driverLocalService;
  }

  // this method is run after each junit class completes execution
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
