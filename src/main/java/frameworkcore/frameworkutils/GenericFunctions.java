package frameworkcore.frameworkutils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericFunctions {

	private static Logger logger = LoggerFactory.getLogger(GenericFunctions.class);

	/*
	 * The below function returns a xpath where the dynamic value is replaced in
	 * provided xpath
	 */
	public static String GetElementWithDynamicXPath(String genericPath, String replaceString) {

		if (null != replaceString) {
			String[] TempArr = replaceString.split("\\|");
			for (int iCount = 0; iCount < TempArr.length; iCount++) {
				genericPath = genericPath.replaceFirst("##PlaceHolder##", TempArr[iCount]);
			}
		}
		return genericPath;
	}

	/*
	 * The below function returns a random number in a provided min and max range
	 */
	public static int RandomNumberGeneratorForGivenRange(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	/*
	 * The below function wait for the specified amount of time in seconds
	 */
	public static void WaitForSeconds(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (Exception e) {
			logger.error(e.getMessage().toString());
		}
	}

	public static void LogAndReportInfo(String infoMessage) {
		logger.info(infoMessage);
		//Reporter.addStepLog(infoMessage);
	}

	public static void LogAndReportError(String errorMessage) {
		logger.error(errorMessage);
		//Reporter.addStepLog(errorMessage);
	}

	public static String GetElementText(WebElement element) {

		String ElementText = null;
		try {
			if (element.isDisplayed()) {
				logger.info(element.toString() + "  Element is Present and displayed");
				if (null != GetAttributeOfElement(element, "innerHTML"))
					ElementText = GetAttributeOfElement(element, "innerHTML");
				else if (null != GetAttributeOfElement(element, "innerText"))
					ElementText = GetAttributeOfElement(element, "innerText");
				else if (null != element.getText())
					ElementText = element.getText();
			} else
				logger.info(element.toString() + "  Element is Present but hidden");
		} catch (Exception e) {
			logger.info("Element is NOT Present");
		}
		return ElementText;
	}

	public static String GetAttributeOfElement(WebElement element, String AttributeName) {
		return element.getAttribute(AttributeName);
	}

	public static String ConvertNumberToINRCurrencyFormat(String number) {
		NumberFormat formatter = NumberFormat.getInstance();
		String moneyString = "INR " + formatter.format(Integer.valueOf(number));
		return moneyString;
	}

	public static String ConvertDateFormat(String strDate) {
		try {
			DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
			DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = originalFormat.parse(strDate);
			return targetFormat.format(date);
		} catch (ParseException pEx) {
			logger.error(pEx.getMessage().toString());
			return null;
		}
	}
	
	public static String getRandomString() {
       // String RandomChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String RandomChar="ABCDEF012345";
        StringBuilder rand = new StringBuilder();
        Random rnd = new Random();
        while (rand.length() < 18) { 
            int index = (int) (rnd.nextFloat() * RandomChar.length());
            rand.append(RandomChar.charAt(index));
        }
        return rand.toString();
    }
	
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
}
