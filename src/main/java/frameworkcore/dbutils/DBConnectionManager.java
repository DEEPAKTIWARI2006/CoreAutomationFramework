/**
 * @author deepaktiwari
 *
 */
package frameworkcore.dbutils;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frameworkcore.excelreader.ExcelDataReaderImpl;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;


public class DBConnectionManager {

  /**
   * 
   */
  Connection conn = null;
  /**
   * 
   */
  Statement stmt = null;
  /**
   * 
   */

  ResultSet rs = null;

  private static Logger logger = LoggerFactory.getLogger(ExcelDataReaderImpl.class);

  /**
   * @throws SQLException
   * @throws IOException
   */
  public ResultSet connectToDBAndGetResultsSet(String sqlQuery) throws IOException {

    final PropertyFileReaderImpl prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    final String connString = "jdbc:postgresql://" + prop.GetProperty("DBServerName") + ":"
        + prop.GetProperty("DBPort") + "/" + prop.GetProperty("DBName");

    try {
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection(connString, prop.GetProperty("DBUserName"),
          prop.GetProperty("DBPswd"));
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sqlQuery);
    } catch (SQLException e) {
      logger.error(e.getClass().getName() + ": " + e.getMessage());
    } catch (ClassNotFoundException e) {
      logger.error(e.getClass().getName() + ": " + e.getMessage());
    }

    return rs;
  }


  public void CloseDBConnection() {
    try {
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      logger.error(e.getClass().getName() + ": " + e.getMessage());
    }
  }
}
