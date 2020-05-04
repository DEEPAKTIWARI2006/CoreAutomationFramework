/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.webdriverfactory;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import frameworkcore.frameworkutils.PropertyFileReaderImpl;

/**
 * This instantiates the ChromeDriver
 */
class ChromeDriverManager {

	private static Logger logger = LoggerFactory.getLogger(ChromeDriverManager.class);

	static WebDriver createDriver(HashMap<String, String> ParamMap)
			throws InvocationTargetException, IllegalAccessException, IOException {
		String OS = System.getProperty("os.name").toLowerCase();
		logger.info("Starting ChromeDriver");

		if (OS.contains("mac"))
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/main/resources/Drivers/chromedriver");
		else if (OS.contains("windows"))
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/main/resources/Drivers/chromedriver.exe");

		WebDriver driver = new ChromeDriver(GetOptions());
		return driver;
	}

	/**
	 * @author deepaktiwari
	 * @throws IOException
	 *
	 */

	private static ChromeOptions GetOptions() throws IOException {

		PropertyFileReaderImpl prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);

		logger.info("Setting Chrome Options");
		ChromeOptions options = new ChromeOptions();

		if (prop.GetProperty("DisableExtensions").equalsIgnoreCase("true"))
			options.addArguments("--disable-extensions");

		if (prop.GetProperty("DisableInfobars").equalsIgnoreCase("true"))
			options.addArguments("disable-infobars");

//		if (prop.GetProperty("SetAcceptInsecureCerts").equalsIgnoreCase("true"))
//			options.setAcceptInsecureCerts(true);
//
//		if (prop.GetProperty("SetUnhandledPromptBehaviour").equalsIgnoreCase("ACCEPT"))
//			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

		if (prop.GetProperty("ChromeSetHeadless").equalsIgnoreCase("true"))
			options.setHeadless(true);
		else
			options.setHeadless(false);

//		if (prop.GetProperty("StartMaximized").equalsIgnoreCase("true"))
//			options.addArguments("--start-maximized");

		// DesiredCapabilities ChromeCapabilities = DesiredCapabilities.chrome();
		// ChromeCapabilities.setPlatform(Platform.WINDOWS);

		// options.merge(ChromeCapabilities);
		return options;

	}
}
