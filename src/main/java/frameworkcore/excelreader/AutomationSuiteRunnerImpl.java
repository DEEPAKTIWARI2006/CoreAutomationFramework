/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.excelreader;

import static frameworkcore.frameworkutils.Constants.CONFIGURATIONSHEETNAME;
import static frameworkcore.frameworkutils.Constants.DRIVERSHEETNAME;
import static frameworkcore.frameworkutils.Messages.EXCELCELLNULL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * This class reads the TestCaseDriverSheet
 */
/**
 * @author deepaktiwari
 *
 */
public class AutomationSuiteRunnerImpl {

  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(AutomationSuiteRunnerImpl.class);
  /**
   * 
   */
  private HashMap<String, String> testsBrowserList = new HashMap<String, String>();
  /**
   * 
   */
  private HashMap<String, HashMap<String, String>> testsParamList =
      new HashMap<String, HashMap<String, String>>();
  /**
   * 
   */
  private Multimap<String, String> testsClassesList = LinkedHashMultimap.create();
  /**
   * 
   */
  private Multimap<String, String> classesMethodList = LinkedHashMultimap.create();
  /**
   * 
   */
  private Hashtable<String, String> testNGConfig = new Hashtable<String, String>();

  /**
   * @throws IOException
   */
  public void getTestCaseInformation() throws IOException {

    String testName = null, className = null, testMethodName = null, testBrowserName = null;
    Row row = null;
    XSSFWorkbook workbook = null;
    FileInputStream file = null;

    if (file == null)
      file = new FileInputStream(new File(DRIVERSHEETNAME));

    workbook = new XSSFWorkbook(file);
    Sheet configsheet = workbook.getSheet(CONFIGURATIONSHEETNAME);
    row = configsheet.getRow(1);

    /**
     * Reading TestNG configuration settings
     */
    logger.info("Parallel mode is " + row.getCell(0).toString());
    logger.info("Threadcount is " + row.getCell(1).toString());
    logger.info("Verbose mode is " + row.getCell(2).toString());
    logger.info("Preserve Order mode is " + row.getCell(3).toString());
    logger.info("Suite Name is " + row.getCell(4).toString());

    try {
      testNGConfig.put("ParallelMode", row.getCell(0).toString());
      testNGConfig.put("ThreadCount", row.getCell(1).toString());
      testNGConfig.put("Verbose", row.getCell(2).toString());
      testNGConfig.put("PreserveOrder", row.getCell(3).toString());
      testNGConfig.put("AutomationSuiteName", row.getCell(4).toString());
    } catch (NullPointerException e) {
      logger.error(EXCELCELLNULL);
      logger.error(e.getMessage().toString());
    }

    /**
     * Reading TestNG class/method and parameters information
     */
    Sheet testCasessheet = workbook.getSheet("TestCaseInfo");
    for (int iNodeLoop = 1; iNodeLoop <= testCasessheet.getLastRowNum(); iNodeLoop++) {
      row = testCasessheet.getRow(iNodeLoop);
      String testCaseExecuteFlag = row.getCell(5).getStringCellValue();

      if (testCaseExecuteFlag.equalsIgnoreCase("Y")) {
        testName = row.getCell(1).getStringCellValue();
        className = row.getCell(3).getStringCellValue();
        testMethodName = row.getCell(4).getStringCellValue();
        testBrowserName = row.getCell(6).getStringCellValue();
        HashMap<String, String> volumnValueMap = new HashMap<String, String>();
        for (int iColumnLoop = 7; iColumnLoop <= row.getLastCellNum() - 1; iColumnLoop++) {
          String cellValue = row.getCell(iColumnLoop, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
              .getStringCellValue();
          String columnmName = testCasessheet.getRow(0).getCell(iColumnLoop).getStringCellValue();
          if (!cellValue.isEmpty())
            volumnValueMap.put(columnmName, cellValue);
        }
        testsParamList.put(testName, volumnValueMap);
        testsBrowserList.put(testName, testBrowserName);
        classesMethodList.put(className, testMethodName);
        testsClassesList.put(testName, className);
      }
    }
    workbook.close();
    file.close();
  }

  /**
   * @return
   */
  public HashMap<String, String> getTestsBrowserList() {
    return testsBrowserList;
  }

  /**
   * @return
   */
  public Multimap<String, String> getTestsClassesList() {
    return testsClassesList;
  }

  /**
   * @return
   */
  public Multimap<String, String> getClassesMethodList() {
    return classesMethodList;
  }

  /**
   * @return
   */
  public Hashtable<String, String> getTestNGConfig() {
    return testNGConfig;
  }

  /**
   * @return
   */
  public HashMap<String, HashMap<String, String>> getTestsParamList() {
    return testsParamList;
  }

}
