/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.webdriverfactory;

import java.util.HashMap;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author deepaktiwari
 *
 */
public class IEDriverManager {

  /**
   * 
   */
  private static WebDriver driver;
  /**
   * 
   */
  private static InternetExplorerOptions options;

  /**
   * @param paramMap
   * @return
   */
  public static WebDriver createDriver(HashMap<String, String> paramMap) {

    System.setProperty("webdriver.ie.driver",
        System.getProperty("user.dir") + "/src/main/resources/Drivers/IEDriverServer.exe");
    driver = new InternetExplorerDriver(getOptions());
    return driver;
  }

  /**
   * @return
   */
  private static InternetExplorerOptions getOptions() {

    options = new InternetExplorerOptions();
    options.introduceFlakinessByIgnoringSecurityDomains();
    options.enableNativeEvents();
    options.enablePersistentHovering();
    options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
    DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
    ieCapabilities.setPlatform(Platform.WINDOWS);

    options.merge(ieCapabilities);
    return options;

  }

}
