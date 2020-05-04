/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.APP;
import static frameworkcore.frameworkutils.Constants.CHROME;
import static frameworkcore.frameworkutils.Constants.FIREFOX;
import static frameworkcore.frameworkutils.Constants.IE;
import static frameworkcore.frameworkutils.Constants.SAFARI;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

/**
 * This class manages the instantiation of the drivers based on the value
 * provided by the TestNG.xml
 */
public class DriverManager {
	
	private static ThreadLocal<WebDriver> Webdriver = new ThreadLocal<WebDriver>();

	public static void setDriver(HashMap<String, String> ParamMap)
			throws InvocationTargetException, IllegalAccessException, IOException {

		String BrowserName = ParamMap.get("BrowserName");
		String PlatformName = ParamMap.get("PlatformName");

		switch (BrowserName) {
		case CHROME:
			if (PlatformName.equalsIgnoreCase("Windows") || PlatformName.equalsIgnoreCase("Mac"))
				setWebDriver(ChromeDriverManager.createDriver(ParamMap));
			else if (PlatformName.equalsIgnoreCase("Android"))
				setWebDriver(AndroidDriverManager.createDriver(ParamMap));
			else if (PlatformName.equalsIgnoreCase("Mac"))
				setWebDriver(iOSDriverManager.createDriver(ParamMap));
			break;

		case FIREFOX:
			setWebDriver(FirefoxDriverManager.createDriver(ParamMap));
			break;

		case IE:
			setWebDriver(IEDriverManager.createDriver(ParamMap));
			break;

		case SAFARI:
			setWebDriver(SafariDriverManager.createDriver(ParamMap));
			break;

		case APP:
			if (PlatformName.equalsIgnoreCase("Android"))
				setWebDriver(AndroidDriverManager.createDriver(ParamMap));
			else if (PlatformName.equalsIgnoreCase("Mac"))
				setWebDriver(iOSDriverManager.createDriver(ParamMap));
			break;
		}
	}

	public static WebDriver getDriver() {
		return Webdriver.get();
	}

	static void setWebDriver(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		Webdriver.set(driver);
	}
}
