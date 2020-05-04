/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.webdriverfactory;

import java.util.HashMap;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This instantiates the FireFoxDriver 
 */
public class FirefoxDriverManager  {
	
	private static Logger logger = LoggerFactory.getLogger(FirefoxDriverManager.class);
	private static WebDriver driver;
	private static FirefoxOptions options;
    
	public static WebDriver createDriver(HashMap<String, String> ParamMap) {
		logger.info("Starting FirefoxDriver" + "  --  " + Thread.currentThread().getId());
		
		if(ParamMap.get("PlatformName").equalsIgnoreCase("Mac"))
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "/src/main/resources/Drivers/geckodriver");
		else if(ParamMap.get("PlatformName").equalsIgnoreCase("Windows"))
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "/src/main/resources/Drivers/geckodriver.exe");
		
        driver = new FirefoxDriver(GetOptions());
        return driver;
    }
    
    private static FirefoxOptions GetOptions() {
    	
    	logger.info("Setting Firefox Options");
    	options = new FirefoxOptions();
//		options.addArguments("--disable-extensions");
//		options.addArguments("test-type");
//		options.addArguments("start-maximized");
//		options.addArguments("disable-infobars");
//		options.setAcceptInsecureCerts(true);
//		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
    	return options;
       
    }

}
