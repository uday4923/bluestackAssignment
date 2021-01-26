package automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.SeleniumWait;

public class BasePage {
	public WebDriver driver;
	protected SeleniumWait wait;

	public BasePage(WebDriver driver, String pageName) {
		this.driver = driver;
		this.wait = new SeleniumWait(driver, Integer.parseInt(LoadProp.getproperty("timeout")));
	}

	public void openUrl() {
		driver.get(LoadProp.getproperty("baseUrl"));
		driver.manage().window().maximize();
		wait.waitForPageToLoad();
	}
	
	public void navigateToPreviousPage() {
		driver.navigate().back();
		wait.waitForPageToLoad();
	}
	
	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void clickLink(WebElement element) {
		System.out.println("Trying to click on Link");
		element.click();
		System.out.println("Clicked successfully");
	}
	
	public String getPageUrl() {
		return driver.getCurrentUrl();
	}
}
