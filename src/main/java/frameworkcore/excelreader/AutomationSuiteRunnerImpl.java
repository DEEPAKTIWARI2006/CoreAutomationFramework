/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.excelreader;

import static frameworkcore.frameworkutils.Constants.*;
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
public class AutomationSuiteRunnerImpl {

	private static Logger logger = LoggerFactory.getLogger(AutomationSuiteRunnerImpl.class);
	private HashMap<String, String> TestsBrowserList = new HashMap<String, String>();
	private HashMap<String, HashMap<String, String>> TestsParamList = new HashMap<String, HashMap<String, String>>();
	private Multimap<String, String> TestsClassesList = LinkedHashMultimap.create();
	private Multimap<String, String> ClassesMethodList = LinkedHashMultimap.create();
	private Hashtable<String, String> TestNGConfig = new Hashtable<String, String>();

	public void GetTestCaseInformation() throws IOException {

		String TestName = null, ClassName = null, TestMethodName = null, TestBrowserName = null;
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
			TestNGConfig.put("ParallelMode", row.getCell(0).toString());
			TestNGConfig.put("ThreadCount", row.getCell(1).toString());
			TestNGConfig.put("Verbose", row.getCell(2).toString());
			TestNGConfig.put("PreserveOrder", row.getCell(3).toString());
			TestNGConfig.put("AutomationSuiteName", row.getCell(4).toString());
		} catch (NullPointerException e) {
			logger.error(EXCELCELLNULL);
			logger.error(e.getMessage().toString());
		}

		/**
		 * Reading TestNG class/method and parameters information
		 */
		Sheet TestCasessheet = workbook.getSheet("TestCaseInfo");
		for (int iNodeLoop = 1; iNodeLoop <= TestCasessheet.getLastRowNum(); iNodeLoop++) {
			row = TestCasessheet.getRow(iNodeLoop);
			String TestCaseExecuteFlag = row.getCell(5).getStringCellValue();

			if (TestCaseExecuteFlag.equalsIgnoreCase("Y")) {
				TestName = row.getCell(1).getStringCellValue();
				ClassName = row.getCell(3).getStringCellValue();
				TestMethodName = row.getCell(4).getStringCellValue();
				TestBrowserName = row.getCell(6).getStringCellValue();
				HashMap<String, String> ColumnValueMap = new HashMap<String, String>();
				for (int iColumnLoop = 7; iColumnLoop <= row.getLastCellNum() - 1; iColumnLoop++) {
					String cellValue = row.getCell(iColumnLoop, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
							.getStringCellValue();
					String columnmName = TestCasessheet.getRow(0).getCell(iColumnLoop).getStringCellValue();
					if (!cellValue.isEmpty())
						ColumnValueMap.put(columnmName, cellValue);
				}
				TestsParamList.put(TestName, ColumnValueMap);
				TestsBrowserList.put(TestName, TestBrowserName);
				ClassesMethodList.put(ClassName, TestMethodName);
				TestsClassesList.put(TestName, ClassName);
			}
		}
		workbook.close();
		file.close();
	}

	public HashMap<String, String> getTestsBrowserList() {
		return TestsBrowserList;
	}

	public Multimap<String, String> getTestsClassesList() {
		return TestsClassesList;
	}

	public Multimap<String, String> getClassesMethodList() {
		return ClassesMethodList;
	}

	public Hashtable<String, String> getTestNGConfig() {
		return TestNGConfig;
	}

	public HashMap<String, HashMap<String, String>> getTestsParamList() {
		return TestsParamList;
	}

}
