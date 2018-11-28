package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;

public class BrowserStackManager {
  
  /**
   * 
   */
  private static WebDriver driver;  
  private static Logger logger = LoggerFactory.getLogger(AndroidDriverManager.class);
  private static PropertyFileReaderImpl prop;
  public static final String userName = "antonyprabhu1";
  public static final String accessKey = "GqqzqzxzmSuxU4GXZFWz";

  /**
   * @param paramMap
   * @return
   * @throws IOException
   */

  public static WebDriver createDriver(HashMap<String, String> paramMap) throws IOException {

    prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    WebDriver driver = new RemoteWebDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), getCapabilities(paramMap));
    return driver;
  }

  /**
   * @param paramMap
   * @return
   * @throws IOException
   */

  private static DesiredCapabilities getCapabilities(HashMap<String, String> paramMap)
      throws IOException {

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("browserName", "android");
    caps.setCapability("device", "Samsung Galaxy S8");
    caps.setCapability("realMobile", "true");
    caps.setCapability("os_version", "7.0");

    return caps;
  }

  

  @AfterTest
  public static void stopServer() {

    try {
      driver.quit();
    } catch (Exception e) {
      logger.error(e.getMessage().toString());
    }
  }

}
