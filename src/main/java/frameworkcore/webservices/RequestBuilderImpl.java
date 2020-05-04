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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import frameworkcore.frameworkutils.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestBuilderImpl {
	
	private static Logger logger = LoggerFactory.getLogger(RequestBuilderImpl.class);
	private String serviceBaseURL = null;
	private RequestSpecification requestSpec = null;
	private RequestSpecBuilder builder = null;
	private Response response = null;
	private String strResponse = null;
	private static String apiToken = null;
	

	public RequestBuilderImpl() {
	}
	
	public void SetBaseURI(String BaseURL) {
		serviceBaseURL = BaseURL;
	}
	
	public void RequestSpecificationBuilder() {
		builder = new RequestSpecBuilder();
		builder.setContentType("application/json");
		requestSpec = builder.build();
	}
	
	public void SetAuthentication(String UserName, String Password) {
		requestSpec.auth().preemptive()
		.basic(UserName, Password);
	}
	
	public void SetRequestType(String serviceRequestType, String apiEndPoint) {
		
		String serviceEndPoint  = serviceBaseURL + apiEndPoint;
		
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
	
	public String ExtractResponseAsString() {
		strResponse = response.then().extract().response().asString();
		return strResponse;
	}
	
	public void SetToken() {
		apiToken = from(strResponse).getString("token");
		System.setProperty("AuthToken", apiToken);
		logger.info("Token is " + apiToken);
	}
	
	public String GetToken() {
		logger.info("Token is " + System.getProperty("AuthToken"));
		return System.getProperty("AuthToken");
	}
	
	public int GetStatusCode() {
		return response.getStatusCode();
	}
	
	public void WriteResponseToFile(String testDataID) {
		try {
            File file=new File(Constants.ACTUALJSONFILESPATH + "/" + testDataID + ".json");  
            file.createNewFile();  
            FileWriter fileWriter = new FileWriter(file); 
            fileWriter.write(strResponse);  
            fileWriter.flush();  
            fileWriter.close(); 
		}catch(IOException e) {
			logger.error("Not able to write to File");
		}
	}
	
	public String ReadResponseValueFromFile(String ExpectedFileName, String xPath) {
		try
		{
			String expectedFilePath = Constants.EXPECTEDJSONFILESPATH + "/" + ExpectedFileName + ".json" ; 
			String ExpectedValue = new String(Files.readAllBytes(Paths.get(expectedFilePath)));
			return from(ExpectedValue).getString(xPath);
		}catch(IOException e) {
			logger.error("Not able to read File");
			return null;
		}
	}
	
	public String ExtractResponseValueAsString(String xPath) {
		return from(strResponse).getString(xPath);
	}
	
	public void SetBody(String FileName) {
		File file = new File(Constants.BODYJSONFILESPATH + "/" + FileName + ".json");
		requestSpec.body(file);
	}
	
	
	public void SetCookies(HashMap<String, String> cookiesInfo) {
		builder.addCookies(cookiesInfo);
	}
	
	public void SetHeaders(String headers) {
		
		String[] apiheaders = headers.split("\\|");
		for(String header:apiheaders) {
			String[] arrHeader = header.split(":");
			if(arrHeader[0].equalsIgnoreCase("Authorization"))
				requestSpec.header(arrHeader[0], "Token " + System.getProperty("AuthToken"));
			else
				requestSpec.header(arrHeader[0], arrHeader[1]);
			
		}
	}
	
	public String GetHeaderValue(String headerName) {
		return response.getHeaders().get(headerName).getName();
	}
	
	public String ExtractValueFromJSON(String xPath) {
		return from(strResponse).getString(xPath);
	}
	
	public List<String> ExtractValuesFromJSON(String xPath) {
		return from(strResponse).getList(xPath);
	}
	
	public void SetPathParams() {
		
	}
	
	public void SetFormParams() {
		
	}
	
	public void SetQueryParams() {
		
	}

}
