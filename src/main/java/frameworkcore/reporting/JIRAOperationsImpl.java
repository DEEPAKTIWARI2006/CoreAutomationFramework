/**
 * 
 */
package frameworkcore.reporting;

import static frameworkcore.frameworkutils.Constants.FRAMEWORKCONFIGFILEPATH;
import java.io.IOException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import frameworkcore.frameworkutils.PropertyFileReaderImpl;
import frameworkcore.webservices.RequestBuilderImpl;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author deepaktiwari
 *
 */
public class JIRAOperationsImpl {

  private static PropertyFileReaderImpl prop = null;
  private RequestSpecification requestSpec = null;
  private static Response response = null;
  private static Logger logger = LoggerFactory.getLogger(JIRAOperationsImpl.class);

  /**
   * @throws IOException
   * 
   */
  public JIRAOperationsImpl() throws IOException {

  }

  public static String logIssueInJIRA(String issueSummary, String issueDescription)
      throws IOException {
    
    Response response=null;
    prop = new PropertyFileReaderImpl(FRAMEWORKCONFIGFILEPATH);
    String endpoint = "/rest/api/2/issue";

    String postJsonRequest = "{\n" + "\"fields\": {\n" + "   \"project\":\n" + "   { \n"
        + "      \"key\": " + "\"" + prop.GetProperty("ProjectKey") + "\"" + "\n" + "   },\n" +
        // " \"assignee\":\n" +
        // " {\"name\": " + "\"" + prop.GetProperty("Assignee") + "\"" + "\n" +
        // " },\n" +
        "   \"summary\": " + "\"" + issueSummary + "\"" + ",\n" + "   \"description\": " + "\""
        + issueDescription + "\"" + ",\n" + "   \"issuetype\": {\n" + "      \"name\": \"Story\"\n"
        + "   },\n" + "   \"components\": [{\"name\": " + "\"" + prop.GetProperty("Component")
        + "\"" + "}]\n" + "  }\n" + "}";

    RestAssured.baseURI = prop.GetProperty("JiraUrl");
    RequestSpecification request = RestAssured.given();
    request.header("Content-Type", "application/json");
    request.auth().preemptive().basic(prop.GetProperty("JiraUserName"),
        prop.GetProperty("JiraPassword"));
    request.body(postJsonRequest);
    response = request.post(endpoint);
    String responseContent = response.asString();
//    System.out.println(responseContent);
    int statusCode = response.getStatusCode();
    if (statusCode == 201) {
      String IssueKey = response.jsonPath().get("key");
      logger.error("Defect logged. JIRA ID is " + IssueKey);
      return IssueKey;
    } else {
      logger.error("Not able to log defect. Status code returned is " + statusCode);
      return null;
    }
  }

}
