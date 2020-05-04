package frameworkcore.webservices;

import org.junit.Assert;

public class ApiAssertions {
	
	
	public void ValidateStatusCode(int expectedStatusCode, int actualStatusCode) {
		Assert.assertEquals("Status Code is not as expected", expectedStatusCode, actualStatusCode);
	}
	
	
	public void ValidateSchema() {
		
	}
	
	public void ValidateValue(String expectedValue, String actualValue) {
		Assert.assertEquals("Actual and Expected value is not same", expectedValue, actualValue);
	}
	
}
