/**
 * @author deepaktiwari
 *
 */

package corepackage;

import static frameworkcore.frameworkutils.Constants.INPUTDATAEXCELPATH;
import static frameworkcore.frameworkutils.Constants.LOG4JCONFPATH;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import frameworkcore.excelreader.ExcelDataReaderImpl;

/**
 * This class prepares the ground work before execution of the test cases start
 */

public class InitializeExecutionImpl {

  /**
   * initializes the logger instance for logging
   */


  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(InitializeExecutionImpl.class);

  /**
   * Below method does the following: Loads the properties from property file Reads the Input excel
   * data in a nested hashmap. Reads the main driver excel sheet
   * 
   * @throws IOException
   */

  /**
   * @throws IOException
   */
  public void InitializeTestExecution() throws IOException {

    logger.info("Starting Automation Suite");

    PropertyConfigurator.configure(LOG4JCONFPATH);
    final ExcelDataReaderImpl datareader = new ExcelDataReaderImpl(INPUTDATAEXCELPATH);
    datareader.readExcelInputData();

    final RunWithTestNGImpl testNGRunner = new RunWithTestNGImpl();
    testNGRunner.executeTests();
    
//    List<String> testFilesList = new ArrayList<String>();
//    testFilesList.add("./TestNG.xml");
//    TestNG testng = new TestNG();
//    testng.setTestSuites(testFilesList);
//    testng.run();
  }
}
