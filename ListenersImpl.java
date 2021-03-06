/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.reporting;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import frameworkcore.webdriverfactory.DriverManager;

/**
 * This is TestNG Listener class
 */
public class ListenersImpl implements ITestListener, IReporter, ISuiteListener{

	private ExtentReports extentreporter=null;
	private static  ExtentTest parentTest;
	public static ExtentTest  childTest;
	private static Logger logger = LoggerFactory.getLogger(ListenersImpl.class);
	ReportingImpl reports;
	
	public ListenersImpl() {
		logger.info("Inside Listeners Constructor. Running thread  " + Thread.currentThread().getId());
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		
		logger.info("Starting method  " + result.getName() + "  --  " + Thread.currentThread().getId());
		parentTest = extentreporter.createTest(result.getName());
		childTest = parentTest.createNode(result.getName());
	}


	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info(result.getName() + " method is Passed" + "  --  " + Thread.currentThread().getId());
		extentreporter = reports.getExtentReporter();
		parentTest.pass(MarkupHelper.createLabel("Test is Passed", ExtentColor.GREEN));
		extentreporter.flush();
	}


	@Override
	public void onTestFailure(ITestResult result) {
		logger.info(result.getName() + " method is Failed" + "  --  " + Thread.currentThread().getId());
		extentreporter = reports.getExtentReporter();
		try {
			parentTest.fail(MarkupHelper.createLabel("Test is Failed", ExtentColor.RED));
			parentTest.fail("Snapshot ", MediaEntityBuilder.createScreenCaptureFromPath(ReportingImpl.CaptureScreenShot(DriverManager.getDriver())).build());
		} catch (IOException e) {
			logger.error(e.getMessage().toString());
		}
		extentreporter.flush();
	}


	@Override
	public void onTestSkipped(ITestResult result) {
		logger.info(result.getName() + "  method is Skipped" + "  --  " + Thread.currentThread().getId());
		//childTest.skip(MarkupHelper.createLabel(result.getName() + " method is Skipped", ExtentColor.GREY));
		extentreporter = reports.getExtentReporter();
		extentreporter.flush();
	}


	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}


	@Override
	public void onStart(ITestContext context) {
		reports= new ReportingImpl();
		extentreporter = reports.getExtentReports(context.getCurrentXmlTest().getName());
		reports.setExtentReport(extentreporter);
		extentreporter = reports.getExtentReporter();
		logger.info("Starting Tests " + context.getName() +  "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void onFinish(ITestContext context) {
		logger.info("Ending Tests " + context.getName() +  "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
	}


	@Override
	public void onStart(ISuite suite) {
		logger.info("Starting Suite " + suite.getName() + "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void onFinish(ISuite suite) {
		logger.info("Finishing Suite " + suite.getName() + "  --  " + Thread.currentThread().getId());
	}
}
