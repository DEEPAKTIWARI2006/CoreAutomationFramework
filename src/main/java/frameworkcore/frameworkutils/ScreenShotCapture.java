/**
 * Core Framework Author : Deepak Tiwari Creation Date : 27 Apr 2018 Modified Date : Modified By :
 */
package frameworkcore.frameworkutils;

import static frameworkcore.frameworkutils.Messages.GENERICERRORMSG;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The below class is used to capture the screenshot and returns the path of the captured screenshot
 */
/**
 * @author deepaktiwari
 *
 */
public class ScreenShotCapture {

  /**
   * 
   */
  private static Logger logger = LoggerFactory.getLogger(ScreenShotCapture.class);

  /**
   * @return
   * @throws AWTException
   * @throws HeadlessException
   */
  public static String takeScreenShot() {

    String ImagePath = System.getProperty("user.dir") + "/Reporting/Screenshots/"
        + getCurrentTimeStamp() + "_ErrorScreenshot.png";
    try {
      BufferedImage image = new Robot()
          .createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
      ImageIO.write(image, "png", new File(ImagePath));
      return ImagePath;
    } catch (IOException e) {
      logger.error(GENERICERRORMSG);
      logger.error(e.getMessage().toString());
      return "";
    } catch (AWTException e) {
      logger.error(GENERICERRORMSG);
      logger.error(e.getMessage().toString());
      return "";
    } catch (HeadlessException e) {
      logger.error(GENERICERRORMSG);
      logger.error(e.getMessage().toString());
      return "";
    }
  }

  /**
   * @return
   */
  private static String getCurrentTimeStamp() {
    return new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(new Date());
  }

}
