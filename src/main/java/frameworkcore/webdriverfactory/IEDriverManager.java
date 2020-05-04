/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.webdriverfactory;

import java.util.HashMap;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IEDriverManager  {
	
	private static WebDriver driver;
	private static InternetExplorerOptions options;
    
	public static WebDriver createDriver(HashMap<String, String> ParamMap) {
        
		System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "/src/main/resources/Drivers/IEDriverServer.exe");
        driver = new InternetExplorerDriver(GetOptions());
        return driver;
    }
    
    private static InternetExplorerOptions GetOptions() {
    	
    	options = new InternetExplorerOptions ();
		options.introduceFlakinessByIgnoringSecurityDomains();
//		options.enableNativeEvents();
		options.enablePersistentHovering();
//		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
		DesiredCapabilities IECapabilities = DesiredCapabilities.internetExplorer();
//		IECapabilities.setPlatform(Platform.WINDOWS);
    	
//    	options.merge(IECapabilities);
    	return options;
       
    }

}
