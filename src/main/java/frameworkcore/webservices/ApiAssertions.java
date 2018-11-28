package frameworkcore.webservices;

import org.junit.Assert;

/**
 * @author deepaktiwari
 *
 */
public class ApiAssertions {


  /**
   * @param expectedStatusCode
   * @param actualStatusCode
   */
  public void validateStatusCode(final int expectedStatusCode, final int actualStatusCode) {
    Assert.assertEquals("Status Code is not as expected", expectedStatusCode, actualStatusCode);
  }

  /**
   * 
   */
  public void validateSchema() {
    // TODO
  }

  /**
   * @param expectedValue
   * @param actualValue
   */
  public void validateValue(String expectedValue, String actualValue) {
    Assert.assertEquals("Actual and Expected value is not same", expectedValue, actualValue);
  }

}
