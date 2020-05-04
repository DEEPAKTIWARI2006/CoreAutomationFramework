package frameworkcore.frameworkutils;

public final class Constants {
	
	public static final String FRAMEWORKCONFIGFILEPATH = System.getProperty("user.dir") + "/src/main/resources/propertyFiles/FrameworkConfiguration.properties";
	public static final String SNAPSHOTPATH = System.getProperty("user.dir") + "Reporting/Screenshots/";
	public static final String DRIVERSHEETNAME = "TestCaseDriverSheet.xlsx";
	public static final String INPUTDATAEXCELPATH = "src/test/resources/inputdata/InputData.xlsx";
	public static final String FEATUREFILESPATH = "src/test/resources/Features";
	public static final String  CONFIGURATIONSHEETNAME = "Configuration";
	
	
	
	//ChromeDriversPath
	public static final String WINCHROMEDRIVERPATH = "src/main/resources/Drivers/chromedriver.exe";
	public static final String MACCHROMEDRIVERPATH = "src/main/resources/Drivers/chromedriver";

	//FireFoxDriversPath
	public static final String WINFIREFOXDRIVERPATH = "src/main/resources/Drivers/geckodriver.exe";
	public static final String MACFIREFOXDRIVERPATH = "src/main/resources/Drivers/geckodriver";

	//IEDriversPath
	public static final String IEDRIVERPATH = "src/main/resources/Drivers/IEDriverServer.exe";

	//SafariDriverPath
	public static final String SAFARIDRIVERPATH = "src/main/resources/Drivers/safaridriver";

	//Log4jConfigPath
	public static final String LOG4JCONFPATH = "src/main/resources/propertyFiles/Log4j.properties";
												
	//ExtentConfigurationFilePath
	public static final String EXTENTCONFIGPATH = "src/main/resources/propertyFiles/extent-config.xml";
	
	// Browser Name
	public static final String CHROME = "Chrome";
	public static final String FIREFOX = "Firefox";
	public static final String IE = "IE";
	public static final String SAFARI = "Safari";
	public static final String ANDROID = "Android";
	public static final String IOS = "iOS";
	public static final String APP = "App";
	public static final String BROWSERSTACK = "BrowserStack";
	public static final String DEFAULTBROWSER = "Chrome";
	
	
	// WebServices
	public static final String APISCHEMAFILEPATH = "src/test/resources/APISchemaFiles";
	public static final String EXPECTEDJSONFILESPATH = "src/test/resources/APIResponseExpectedJsonFiles";
	public static final String ACTUALJSONFILESPATH = "src/test/resources/APIResponseActualJsonFiles";
	public static final String BODYJSONFILESPATH = "src/test/resources/inputdata/jsonFiles";
	
	
	
	

}
