/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package corepackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import frameworkcore.excelreader.AutomationSuiteRunnerImpl;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;


public class RunWithTestNGImpl {
	
	private static Logger logger = LoggerFactory.getLogger(RunWithTestNGImpl.class);
	private static HashMap<String, String> TestsBrowserList ;
	private static HashMap<String, HashMap<String, String>> TestsParamList ;
	private static Multimap<String, String> TestsClassesList = ArrayListMultimap.create();
	private static Multimap<String, String> ClassesMethodList = ArrayListMultimap.create();
	private static Hashtable<String, String> TestNGConfig = new Hashtable<String, String>();
	
	/**
	 * This method gets the information from the "GetTestCaseInformation()" method.
	 * The information contains the class and method names to run from excel
	 * The information also contains the TestNG configuration information from the excel
	 * It creates the TestNG.xml file based on the information gathered
	 * Finally it triggers the execution through TestNG 
	 */
	public void ExecuteTests() throws IOException{
		
		AutomationSuiteRunnerImpl testcaseinfo = new AutomationSuiteRunnerImpl();
		testcaseinfo.GetTestCaseInformation();
		TestsBrowserList = testcaseinfo.getTestsBrowserList();
		TestsClassesList = testcaseinfo.getTestsClassesList();
		ClassesMethodList = testcaseinfo.getClassesMethodList();
		TestsParamList = testcaseinfo.getTestsParamList();
		TestNGConfig = testcaseinfo.getTestNGConfig();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		
		logger.info("Generating TestNG.xml file");
		
		/**
		 * The below code creates testNG xmlSuite and sets the configuration
		 */
		XmlSuite suite = new XmlSuite();
		suite.setName(TestNGConfig.get("AutomationSuiteName"));
		
		if(TestNGConfig.get("ParallelMode").toString().equalsIgnoreCase("True")) {
			suite.setParallel(XmlSuite.ParallelMode.TESTS);
			suite.setThreadCount(Integer.parseInt(TestNGConfig.get("ThreadCount")));
		}
		else {

			suite.setParallel(XmlSuite.ParallelMode.NONE);
		}
		
		suite.setVerbose(Integer.parseInt(TestNGConfig.get("Verbose")));
		if(TestNGConfig.get("PreserveOrder").toString().equalsIgnoreCase("True"))
			suite.setPreserveOrder(Boolean.TRUE);
		else 
			suite.setPreserveOrder(Boolean.FALSE);
		
		
		//suite.addListener("frameworkcore.ReportingClass.ListenersImpl");
		
		/**
		 * The below code assigns the Test classes/mathods/parameters to the TestNG Suite
		 */
		for (String key : TestsBrowserList.keySet()){
			ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
			XmlTest test = new XmlTest(suite);
			test.setName(key.toString());
			HashMap<String,String> browserinfo = new HashMap<String,String>();
			browserinfo.put("BrowserName", TestsBrowserList.get(key).toString());
			test.setParameters(browserinfo);
			SetParameters(test);
			Collection<String> classvalues =  TestsClassesList.get(key);
			for(String testclass : classvalues){
				XmlClass testClass = new XmlClass();
		        testClass.setName(testclass);
		        Collection<String> methodvalues =  ClassesMethodList.get(testclass);
		        ArrayList<XmlInclude> methodsToRun = new ArrayList<XmlInclude>();
		        for(String method: methodvalues){
		        	if(method.toLowerCase().contains("tag")||method.toLowerCase().contains("ignore"))
		        		logger.info("Method  " + method + "  will not run" );
		        	else
		        		methodsToRun.add(new XmlInclude(method));
		        }
		        testClass.setIncludedMethods(methodsToRun);
		        
		        classes.add(testClass);
			}
			test.setXmlClasses(classes);
		}
		
		suites.add(suite);
		
		/**
		 * Writing the TestNG.xml information to a file
		 */
		 FileWriter writer;
		 File TestNGFile = new File("TestNG.xml");
		 if(TestNGFile.exists())						// Delete TestNG if exists
			 TestNGFile.delete();
		 
			writer = new FileWriter(TestNGFile);
			 writer.write(suite.toXml());
		        writer.flush();
		        writer.close();
		        logger.info("TestNG file Generated Successfully");
		
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();  
		
	}
	
	/**
	 * Iterates through the test cases and sets parameters for them
	 */
	private void SetParameters(XmlTest test){
		 for (Map.Entry<String,String> entry : TestsParamList.get(test.getName()).entrySet()){
			 test.addParameter(entry.getKey(), entry.getValue());
		 }
	}
}
