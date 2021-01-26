package automation;

import org.openqa.selenium.WebDriver;

import pages.GamePage;
import pages.HomePage;

public class TestSessionInitiator {
	WebDriverFactory webDriverFactory;
	public WebDriver driver;
	public HomePage homePage;
	public GamePage gamePage;

	public TestSessionInitiator() {
		webDriverFactory = new WebDriverFactory();
		driver = webDriverFactory.getDriver();
		homePage = new HomePage(driver);
		gamePage = new GamePage(driver);
	}
}
