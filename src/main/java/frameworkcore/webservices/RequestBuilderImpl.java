package frameworkcore.webservices;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frameworkcore.frameworkutils.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author deepaktiwari
 *
 */
public class RequestBuilderImpl {

	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(RequestBuilderImpl.class);
	/**
	 * 
	 */
	private static String serviceBaseURL = null;
	/**
	 * 
	 */
	private static RequestSpecification requestSpec = null;
	/**
	 * 
	 */
	private static RequestSpecBuilder builder = null;
	/**
	 * 
	 */
	private static Response response = null;
	/**
	 * 
	 */
	private static String strResponse = null;
	/**
	 * 
	 */
	private static String apiToken = null;

	/**
	 * 
	 */
	public RequestBuilderImpl() {
	}

	/**
	 * @param BaseURL
	 */
	public void setBaseURI(String baseURL) {
		serviceBaseURL = baseURL;
	}

	/**
	 * 
	 */
	public void requestSpecificationBuilder() {
		builder = new RequestSpecBuilder();
		builder.setContentType("application/json");
		requestSpec = builder.build();
	}

	/**
	 * @param UserName
	 * @param Password
	 */
	public void setAuthentication(String userName, String password) {
		requestSpec.auth().preemptive().basic(userName, password);
	}

	/**
	 * @param serviceRequestType
	 * @param apiEndPoint
	 */
	public void setRequestType(String serviceRequestType, String apiEndPoint) {

		String serviceEndPoint = serviceBaseURL + apiEndPoint;

		switch (serviceRequestType) {

		case "GET":
			response = given().relaxedHTTPSValidation().spec(requestSpec).when().get(serviceEndPoint);
			break;

		case "POST":
			response = given().relaxedHTTPSValidation().spec(requestSpec).when().post(serviceEndPoint);
			break;

		case "PUT":
			break;

		case "DELETE":
			break;

		}
	}

	/**
	 * @return
	 */
	public String extractResponseAsString() {
		strResponse = response.then().extract().response().asString();
		return strResponse;
	}

	/**
	 * 
	 */
	public void setToken() {
		apiToken = from(strResponse).getString("token");
		System.setProperty("AuthToken", apiToken);
		logger.info("Token is " + apiToken);
	}

	/**
	 * @return
	 */
	public String getToken() {
		logger.info("Token is " + System.getProperty("AuthToken"));
		return System.getProperty("AuthToken");
	}

	/**
	 * @return
	 */
	public int getStatusCode() {
		return response.getStatusCode();
	}

	/**
	 * @param testDataID
	 */
	public void writeResponseToFile(String testDataID) {
		try {
			File file = new File(Constants.ACTUALJSONFILESPATH + "/" + testDataID + ".json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(strResponse);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			logger.error("Not able to write to File");
		}
	}

	/**
	 * @param ExpectedFileName
	 * @param xPath
	 * @return
	 */
	public String readResponseValueFromFile(String expectedFileName, String xPath) {
		try {
			String expectedFilePath = Constants.EXPECTEDJSONFILESPATH + "/" + expectedFileName + ".json";
			String expectedValue = new String(Files.readAllBytes(Paths.get(expectedFilePath)));
			return from(expectedValue).getString(xPath);
		} catch (IOException e) {
			logger.error("Not able to read File");
			return null;
		}
	}

	/**
	 * @param xPath
	 * @return
	 */
	public String extractResponseValueAsString(String xPath) {
		return from(strResponse).getString(xPath);
	}

	/**
	 * @param FileName
	 */
	public void setBody(String fileName) {
		File file = new File(Constants.BODYJSONFILESPATH + "/" + fileName + ".json");
		requestSpec.body(file);
	}

	/**
	 * @param cookiesInfo
	 */
	public void setCookies(HashMap<String, String> cookiesInfo) {
		builder.addCookies(cookiesInfo);
	}

	/**
	 * @param headers
	 */
	public void setHeaders(String headers) {

		String[] apiheaders = headers.split("\\|");
		for (String header : apiheaders) {
			String[] arrHeader = header.split(":");
			if (arrHeader[0].equalsIgnoreCase("Authorization"))
				requestSpec.header(arrHeader[0], "Token " + System.getProperty("AuthToken"));
			else
				requestSpec.header(arrHeader[0], arrHeader[1]);

		}
	}

	/**
	 * @param headerName
	 * @return
	 */
	public String getHeaderValue(String headerName) {
		return response.getHeaders().get(headerName).getName();
	}

	/**
	 * @param xPath
	 * @return
	 */
	public String extractValueFromJSON(String xPath) {
		return from(strResponse).getString(xPath);
	}

	/**
	 * @param xPath
	 * @return
	 */
	public List<String> extractValuesFromJSON(String xPath) {
		return from(strResponse).getList(xPath);
	}

	public int getTotalRecords() {
      int total = 0;
      JsonPath jp = new JsonPath(response.asString());
      String rows = jp.get("data.id.size()").toString();
      total = Integer.parseInt(rows);
      return total;
  }
  public int getCountForKeyTotal() {
      int count = 0;
      JsonPath jp = new JsonPath(response.asString());
      String value = jp.get("Total").toString();
      count = Integer.parseInt(value);
      return count;
  }
  
  public int getCountForKeyNametotal() {
      int count = 0;
      JsonPath jp = new JsonPath(response.asString());
      String value = jp.get("total").toString();
      count = Integer.parseInt(value);
      return count;
  }
  
  public int getTotalSizeOfResponseList() {
      int total = 0;
      JsonPath jp = new JsonPath(response.asString());
      String rows = jp.get("data.companyList.id.size()").toString();
      total = Integer.parseInt(rows);
      return total;
  }
  
  public int getAPIResponseCountForSmallCap() {
      int total = 0;
      JsonPath jp = new JsonPath(response.asString());
      String rows = jp.get("SMALLCAP.id.size()").toString();
      total = Integer.parseInt(rows);
      return total;
  }
  
  public int getAPIResponseCountForLargeCap() {
      int total = 0;
      JsonPath jp = new JsonPath(response.asString());
      String rows = jp.get("LARGECAP.id.size()").toString();
      total = Integer.parseInt(rows);
      return total;
  }
  
  public int getResultCount(String key) {
    int total = 0;
    JsonPath jp = new JsonPath(response.asString());
    String count = jp.get(key).toString();
    total = Integer.parseInt(count);
    return total;
   
}
}
