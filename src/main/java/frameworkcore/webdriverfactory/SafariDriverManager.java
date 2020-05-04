/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.webdriverfactory;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SafariDriverManager  {
	
	private static WebDriver driver;
	private static SafariOptions options;
	private static Logger logger = LoggerFactory.getLogger(SafariDriverManager.class);
    
	public static WebDriver createDriver(HashMap<String, String> ParamMap) {
        
		logger.info("Starting SafariDriver" + "  --  " + Thread.currentThread().getId());
        driver = new SafariDriver(GetOptions());
        return driver;
    }
    
    private static SafariOptions GetOptions() {
    	
    	options = new SafariOptions();
		DesiredCapabilities SafariCapabilities = DesiredCapabilities.safari();
		SafariCapabilities.setCapability(SafariOptions.CAPABILITY, options); 
    	//options.merge(SafariCapabilities);
    	return options;
       
    }

}
