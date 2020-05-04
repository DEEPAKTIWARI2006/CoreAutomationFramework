/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.frameworkutils;

import static frameworkcore.frameworkutils.Constants.*;
import static frameworkcore.frameworkutils.Messages.*;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The below class is used to capture the screenshot and returns the path of the captured screenshot
 */
public class ScreenShotCapture {
	
	private static Logger logger = LoggerFactory.getLogger(ScreenShotCapture.class);
	
	public static String TakeScreenShot(){
		
		String ImagePath = System.getProperty("user.dir") +"/Reporting/Screenshots/" + GetCurrentTimeStamp() + "_ErrorScreenshot.png";
		try{
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(ImagePath));
			return ImagePath;
		}catch(Exception e){
			logger.error(GENERICERRORMSG);
			logger.error(e.getMessage().toString());
			return "";
		}
	}
	
	private static String GetCurrentTimeStamp(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

}
