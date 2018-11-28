/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
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
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import frameworkcore.excelreader.AutomationSuiteRunnerImpl;

public class RunWithTestNGImpl {

  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(RunWithTestNGImpl.class);
  /**
   * 
   */
  private static HashMap<String, String> testsBrowserList;
  /**
   * 
   */
  private static HashMap<String, HashMap<String, String>> testsParamList;
  /**
   * 
   */
  private static Multimap<String, String> testsClassesList = ArrayListMultimap.create();
  /**
   * 
   */
  private static Multimap<String, String> classesMethodList = ArrayListMultimap.create();
  /**
   * 
   */
  private static Hashtable<String, String> testNGConfig = new Hashtable<String, String>();

  /**
   * This method gets the information from the "GetTestCaseInformation()" method. The information
   * contains the class and method names to run from excel The information also contains the TestNG
   * configuration information from the excel It creates the TestNG.xml file based on the
   * information gathered Finally it triggers the execution through TestNG
   */
  /**
   * @throws IOException
   */
  public void executeTests() throws IOException {

    AutomationSuiteRunnerImpl testcaseinfo = new AutomationSuiteRunnerImpl();
    testcaseinfo.getTestCaseInformation();
    testsBrowserList = testcaseinfo.getTestsBrowserList();
    testsClassesList = testcaseinfo.getTestsClassesList();
    classesMethodList = testcaseinfo.getClassesMethodList();
    testsParamList = testcaseinfo.getTestsParamList();
    testNGConfig = testcaseinfo.getTestNGConfig();
    List<XmlSuite> suites = new ArrayList<XmlSuite>();

    logger.info("Generating TestNG.xml file");

    /**
     * The below code creates testNG xmlSuite and sets the configuration
     */
    final XmlSuite suite = new XmlSuite();
    suite.setName(testNGConfig.get("AutomationSuiteName"));

    suite.setVerbose(Integer.parseInt(testNGConfig.get("Verbose")));
    if (testNGConfig.get("PreserveOrder").toString().equalsIgnoreCase("True"))
      suite.setPreserveOrder(Boolean.TRUE);
    else
      suite.setPreserveOrder(Boolean.FALSE);

    // suite.addListener("frameworkcore.ReportingClass.ListenersImpl");

    /**
     * The below code assigns the Test classes/mathods/parameters to the TestNG Suite
     */
    for (String key : testsBrowserList.keySet()) {
      ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
      XmlTest test = new XmlTest(suite);
      test.setName(key);
      test.setParallel(ParallelMode.NONE);
//      test.setThreadCount(1);
      HashMap<String, String> browserinfo = new HashMap<String, String>();
      browserinfo.put("BrowserName", testsBrowserList.get(key).toString());
      test.setParameters(browserinfo);
      setParameters(test);
      final Collection<String> classvalues = testsClassesList.get(key);
      for (final String testclass : classvalues) {
        XmlClass testClass = new XmlClass();
        testClass.setName(testclass);
        
        Collection<String> methodvalues = classesMethodList.get(testclass);
        ArrayList<XmlInclude> methodsToRun = new ArrayList<XmlInclude>();
        for (String method : methodvalues) {
          if (method.toLowerCase().contains("tag") || method.toLowerCase().contains("ignore"))
            logger.info("Method  " + method + "  will not run");
          else
            methodsToRun.add(new XmlInclude(method));
        }
        testClass.setIncludedMethods(methodsToRun);
        
        classes.add(testClass);
      }
      test.setXmlClasses(classes);
    }
    
    if (testNGConfig.get("ParallelMode").toString().equalsIgnoreCase("True")) {
      suite.setParallel(XmlSuite.ParallelMode.TESTS);
      suite.setThreadCount(Integer.parseInt(testNGConfig.get("ThreadCount")));
    } else {
      suite.setParallel(XmlSuite.ParallelMode.NONE);
    }
    suites.add(suite);
    
    /**
     * Writing the TestNG.xml information to a file
     */
    FileWriter writer;
    File testNGFile = new File("TestNG.xml");
    if (testNGFile.exists()) // Delete TestNG if exists
      testNGFile.delete();

    writer = new FileWriter(testNGFile);
    writer.write(suite.toXml());
    writer.flush();
    writer.close();
    logger.info("TestNG file Generated Successfully");

    TestNG tng = new TestNG();
    tng.setXmlSuites(suites);
    tng.run();

  }

  /**
   * Iterates through the test cases and sets parameters
   */
  /**
   * @param test
   */
  private void setParameters(XmlTest test) {
    for (Map.Entry<String, String> entry : testsParamList.get(test.getName()).entrySet()) {
      test.addParameter(entry.getKey(), entry.getValue());
    }
  }
}
