/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.reporting;

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

/**
 * This is TestNG Listener class
 */
public class ListenersImpl implements ITestListener, IReporter, ISuiteListener{

	private static Logger logger = LoggerFactory.getLogger(ListenersImpl.class);

	
	public ListenersImpl() {
		logger.info("Inside Listeners Constructor. Running thread  " + Thread.currentThread().getId());
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		
		logger.info("Starting method  " + result.getName() + "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info(result.getName() + " method is Passed" + "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void onTestFailure(ITestResult result) {
		logger.info(result.getName() + " method is Failed" + "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void onTestSkipped(ITestResult result) {
		logger.info(result.getName() + "  method is Skipped" + "  --  " + Thread.currentThread().getId());
	}


	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}


	@Override
	public void onStart(ITestContext context) {
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
