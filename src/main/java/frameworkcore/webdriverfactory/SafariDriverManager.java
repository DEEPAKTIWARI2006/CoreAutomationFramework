/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.webdriverfactory;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author deepaktiwari
 *
 */
public class SafariDriverManager {

  /**
   * 
   */
  private static WebDriver driver;
  /**
   * 
   */
  private static SafariOptions options;
  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(SafariDriverManager.class);

  /**
   * @param paramMap
   * @return
   */
  public static WebDriver createDriver(HashMap<String, String> paramMap) {

    logger.info("Starting SafariDriver" + "  --  " + Thread.currentThread().getId());
    driver = new SafariDriver(getOptions());
    return driver;
  }

  /**
   * @return
   */
  private static SafariOptions getOptions() {

    options = new SafariOptions();

    return options;

  }

}
