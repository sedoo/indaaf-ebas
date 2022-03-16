package indaaf;

import org.junit.Assert;
import org.junit.Test;

public class TestRegex {

	@Test
	public void basicTest() {
		String regex = "^[^:]+:\\s+.*";
		boolean matches = "#Resolution code:              1d".matches(regex);
		Assert.assertTrue(matches);

	}
	
}
