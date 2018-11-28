/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.excelreader;

import static frameworkcore.frameworkutils.Messages.READEXCELINPUTDATA;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The below class is responsible for gathering the Test input data
 */
/**
 * @author deepaktiwari
 *
 */
public class ExcelDataReaderImpl {

  /**
   * 
   */
  private static HashMap<String, HashMap<String, HashMap<String, String>>> inputData =
      new HashMap<String, HashMap<String, HashMap<String, String>>>();
  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(ExcelDataReaderImpl.class);
  /**
   * 
   */
  private String excelFilePath = null;

  /**
   * @param ExcelFilePath
   */
  public ExcelDataReaderImpl(final String excelFilePath) {
    this.excelFilePath = excelFilePath;
  }

  /**
   * @throws IOException
   */
  public void readExcelInputData() throws IOException {

    logger.info(READEXCELINPUTDATA);
    int iterator;
    String sheetName, cellValue;
    Row row = null;
    XSSFWorkbook workbook = null;
    FileInputStream file = null;

    if (file == null)
      file = new FileInputStream(new File(excelFilePath));
    workbook = new XSSFWorkbook(file);

    for (iterator = 1; iterator < workbook.getNumberOfSheets() - 1; iterator++) {
      sheetName = workbook.getSheetName(iterator);
      Sheet sheet = workbook.getSheet(sheetName);
      logger.info("Reading Input Data sheet " + sheetName);
      HashMap<String, HashMap<String, String>> testCaseMap =
          new HashMap<String, HashMap<String, String>>();

      for (int iRowLoop = 1; iRowLoop <= sheet.getPhysicalNumberOfRows(); iRowLoop++) {

        HashMap<String, String> columnValueMap = new HashMap<String, String>();
        row = sheet.getRow(iRowLoop);
        if (row != null) {
          for (int iColumnLoop = 1; iColumnLoop <= row.getLastCellNum() - 1; iColumnLoop++) {
            String columnmName = sheet.getRow(0).getCell(iColumnLoop).getStringCellValue();
            
            cellValue = row.getCell(iColumnLoop, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                .getStringCellValue();
            logger.info("Input Data sheet " + sheetName + " -- Cell Row " + iRowLoop
                + " and column " + columnmName + " has value " + cellValue);
            if (!cellValue.isEmpty())
              columnValueMap.put(columnmName, cellValue);
          }
          String testCaseName = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
          testCaseMap.put(testCaseName, columnValueMap);
        }
      }
      inputData.put(sheetName, testCaseMap);
    }
    workbook.close();
    file.close();
  }

  /**
   * The below method returns the value from the HashMap parameter "param" should be in format
   * "sheetName.TestCaseID.ColumnName"
   */
  /**
   * @param param
   * @return
   */
  public static String GetDataValue(String param) {
    String[] arr = param.split("\\.");
    return inputData.get(arr[0]).get(arr[1]).get(arr[2]);
  }

}
