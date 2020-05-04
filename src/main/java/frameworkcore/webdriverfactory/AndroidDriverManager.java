/**
 * This project is using Spring 
 */
package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

/**
 * @author dtiwa1
 *
 */
public class AndroidDriverManager  {
	
	private static WebDriver driver;
	private static AppiumDriverLocalService driverLocalService;
	private static Logger logger = LoggerFactory.getLogger(AndroidDriverManager.class);
	private static PropertyFileReaderImpl prop;
	
	public static WebDriver createDriver(HashMap<String, String> ParamMap) throws IOException {
		
		prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
		driverLocalService = startAppiumServer(ParamMap);
        driver = new AndroidDriver<MobileElement>(driverLocalService, GetCapabilities(ParamMap));
        //driver.get("https://www.guru99.com/introduction-to-appium.html");
        return driver;
    }
    
    private static DesiredCapabilities GetCapabilities(HashMap<String, String> ParamMap) throws IOException {

    	
		DesiredCapabilities capabilities = DesiredCapabilities.android();
		capabilities.setPlatform(Platform.ANDROID);
		capabilities.setCapability("automationName", "uiautomator2");
		
		if(ParamMap.get("BrowserName").equalsIgnoreCase("Chrome"))		
			capabilities.setBrowserName("Chrome");
		else if(ParamMap.get("BrowserName").equalsIgnoreCase("App"))
			capabilities.setCapability("app", ParamMap.get("AppPath"));
		
		capabilities.setCapability("deviceName", ParamMap.get("DeviceName"));
		capabilities.setCapability("platformVersion", ParamMap.get("PlatformVersion"));
		
		if(prop.GetProperty("AndroidfullReset").equalsIgnoreCase("true"))
			capabilities.setCapability("fullReset", true);
		
		if(prop.GetProperty("AndroidunicodeKeyboard").equalsIgnoreCase("true"))
			capabilities.setCapability("unicodeKeyboard", true);
		
		if(prop.GetProperty("AndroidresetKeyboard").equalsIgnoreCase("true"))
			capabilities.setCapability("resetKeyboard", true);
		
		if(prop.GetProperty("AndroidnoReset").equalsIgnoreCase("true"))
			capabilities.setCapability("noReset", true);
		
    	return capabilities;
    }
    
    public static AppiumDriverLocalService startAppiumServer(HashMap<String, String> ParamMap){
		
    	driverLocalService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
    	    	.withLogFile(new File(System.getProperty("user.dir") + prop.GetProperty("AppiumLogPath").toString()))
    	    	.withIPAddress(prop.GetProperty("IPAddress").toString())
    	    	.usingAnyFreePort()
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withArgument(GeneralServerFlag.LOG_LEVEL, prop.GetProperty("LogLevel").toString())
				.withArgument(GeneralServerFlag.LOG_TIMESTAMP));
    	
    	driverLocalService.start();
    		return driverLocalService;
    }
 
    //this method is run after each junit class completes execution
    @AfterTest
    public static void stopServer() {
    	
    	try{
        	driver.quit();
    		driverLocalService.stop();
    	}catch(Exception e){
    		logger.error(e.getMessage().toString());
    	}
    }
}
