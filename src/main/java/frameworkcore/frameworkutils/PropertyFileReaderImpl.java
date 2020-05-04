/**
 * Core Framework
 * Author : Deepak Tiwari
 * Creation Date : 27 Apr 2018
 * Modified Date : 
 * Modified By : 
 */
package frameworkcore.frameworkutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The below class reads the Framework specific properties
 */
public class PropertyFileReaderImpl {
	
	private Logger logger = LoggerFactory.getLogger(PropertyFileReaderImpl.class);
	Map<String,String> map = null;
	String propertyFilePath = null;
	
	public PropertyFileReaderImpl(String propertyFilePath) throws IOException {
		this.propertyFilePath = propertyFilePath;
		logger.info("Reading Property File");
        Properties prop = new Properties();
        map = new HashMap<String,String>();
        FileInputStream inputStream = new FileInputStream(propertyFilePath);
        prop.load(inputStream);
        
        for (final Entry<Object, Object> entry : prop.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
	}
	
	
	public String GetProperty(String key) {
		return map.get(key);
	}
}
