package frameworkcore.frameworkutils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vimalselvam.cucumber.listener.Reporter;
import frameworkcore.webdriverfactory.DriverManager;

/**
 * @author deepaktiwari
 *
 */
public class GenericFunctions {

  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(GenericFunctions.class);

  /*
   * The below function returns a xpath where the dynamic value is replaced in provided xpath
   */
  /**
   * @param genericPath
   * @param replaceString
   * @return
   */
  public static String GetElementWithDynamicXPath(String genericPath, String replaceString) {

    if (null != replaceString) {
      String[] TempArr = replaceString.split("\\|");
      for (int iCount = 0; iCount < TempArr.length; iCount++) {
        try {
          genericPath = genericPath.replaceFirst("##PlaceHolder##", TempArr[iCount]);
        } catch (Exception e) {
          genericPath = genericPath.replace("##PlaceHolder##", TempArr[iCount]);
        }

      }
    }
    return genericPath;
  }

  /*
   * The below function returns a random number in a provided min and max range
   */
  /**
   * @param min
   * @param max
   * @return
   */
  public static int RandomNumberGeneratorForGivenRange(int min, int max) {
    Random rand = new Random();
    return rand.nextInt((max - min) + 1) + min;
  }

  /*
   * The below function wait for the specified amount of time in seconds
   */
  /**
   * @param secs
   * @throws InterruptedException
   */
  public static void WaitForSeconds(int secs) {

    try {
      Thread.sleep(secs * 1000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage().toString());
    }
  }

  /**
   * @param infoMessage
   */
  public static void LogAndReportInfo(String infoMessage) {
    String reportMessage = "";
    int i = 0;
    String[] infoMessageArr = infoMessage.trim().split(" ");
    logger.info(infoMessage);
    for (int iCount = 0; iCount < infoMessageArr.length; iCount++) {
      reportMessage = reportMessage + " " + infoMessageArr[iCount];
      if (i > 15) {
        reportMessage = reportMessage + System.lineSeparator();
        i = 0;
      }
      i++;
    }

    Reporter.addStepLog(reportMessage);
  }

  /**
   * @param errorMessage
   */
  public static void LogAndReportError(String errorMessage) {
    logger.error(errorMessage);
    Reporter.addStepLog(errorMessage);
  }

  /**
   * @param element
   * @return
   */
  public static String GetElementText(WebElement element) {

    String ElementText = null;
    try {
      if (element.isDisplayed()) {
        logger.info(element.toString() + "  Element is Present and displayed");
        if (GetAttributeOfElement(element, "innerHTML") != null)
          ElementText = GetAttributeOfElement(element, "innerHTML");
        else if (GetAttributeOfElement(element, "innerText") != null)
          ElementText = GetAttributeOfElement(element, "innerText");
        else if (element.getText() != null)
          ElementText = element.getText();
      } else
        logger.info(element.toString() + "  Element is Present but hidden");
    } catch (Exception e) {
      logger.info("Element is NOT Present");
    }
    return ElementText;
  }

  /**
   * @param element
   * @param AttributeName
   * @return
   */
  public static String GetAttributeOfElement(WebElement element, String AttributeName) {
    return element.getAttribute(AttributeName);
  }

  /**
   * @param number
   * @return
   */
  public static String ConvertNumberToINRCurrencyFormat(String number) {
    NumberFormat formatter = NumberFormat.getInstance();
    String moneyString = "INR " + formatter.format(Integer.valueOf(number));
    return moneyString;
  }

  /**
   * @param strDate
   * @return
   */
  public static String convertDateFormat(String strDate) {

    try {
      DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
      DateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
      Date date = originalFormat.parse(strDate);
      return targetFormat.format(date);
    } catch (ParseException pEx) {
      logger.error(pEx.getMessage().toString());
      return null;
    }
  }

  /**
   * @return
   */
  public static String getRandomString() {
    String randomChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder rand = new StringBuilder();
    Random rnd = new Random();
    while (rand.length() < 18) {
      int index = (int) (rnd.nextFloat() * randomChar.length());
      rand.append(randomChar.charAt(index));
    }
    return rand.toString();
  }

  /**
   * @param act
   * @param exp
   * @return
   */
  public static boolean containsIgnoreCase(String act, String exp) {
    final int length = exp.length();
    if (length == 0)
      return true; // Empty string is contained

    final char firstLo = Character.toLowerCase(exp.charAt(0));
    final char firstUp = Character.toUpperCase(exp.charAt(0));
    for (int i = act.length() - length; i >= 0; i--) {
      final char ch = act.charAt(i);
      if (ch != firstLo && ch != firstUp)
        continue;

      if (act.regionMatches(true, i, exp, 0, length))
        return true;
    }
    return false;
  }

  public static String reverseString(String stringToReverse) {
    StringBuilder sb = new StringBuilder(stringToReverse);
    sb = sb.reverse();
    return sb.toString();
  }

  public static String convertDateFormat(String oldFormat, String newFormat, String date) {
    DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern(oldFormat);
    DateTimeFormatter newPattern = DateTimeFormatter.ofPattern(newFormat);
    LocalDate datetime = LocalDate.parse(date, oldPattern);
    return datetime.format(newPattern).toString();
  }
 
  public static void printThreadID(WebDriver driver) {
    logger
    .info(DriverManager.getDriverType(driver) + " Thread -- " + Thread.currentThread().getId());
  }

}
