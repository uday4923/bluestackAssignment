package tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterTest;

import automation.TestSessionInitiator;

public class BaseTest extends TestSessionInitiator {

	public void printResult(Map<String, List<String>> resultData) {
		for (Map.Entry<String, List<String>> entry : resultData.entrySet()) {
			System.out.print(entry.getKey() + " : " + entry.getValue());
			System.out.println("");
		}
	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}

}
