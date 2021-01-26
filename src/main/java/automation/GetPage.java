package automation;


import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import util.ObjectFileReader;
import util.SeleniumWait;

public class GetPage extends BasePage {
	String pageName;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.pageName = pageName;
	}

	protected WebElement element(String elementToken, String... replacements) throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(driver.findElement(getLocator(elementToken, replacements)));
		} catch (NoSuchElementException excp) {
			System.out.println("FAILED: Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}

		return elem;
	}

	protected WebElement element(WebElement element, String elementToken, String... replacements) throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(element.findElement(getLocator(elementToken, replacements)));
		} catch (NoSuchElementException excp) {
			System.out.println("FAILED: Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}

		return elem;
	}
	
	protected boolean checkIfElementIsNotThere(String eleString) {
		boolean flag = false;
		try {
			if (driver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (NoSuchElementException ex) {
			flag = true;
		}
		return flag;
	}

	protected boolean checkIfElementIsNotThere(String eleString, String replacement) {
		boolean flag = false;
		try {
			if (driver.findElement(getLocator(eleString, replacement)).isDisplayed()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (NoSuchElementException ex) {
			flag = true;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString) {
		boolean flag = false;
		try {
			if (driver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString, String replacement) {
		boolean flag = false;
		try {
			if (driver.findElement(getLocator(eleString, replacement)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	protected List<WebElement> elements(String elementToken, String... replacements) {
		return wait.waitForElementsToBeVisible(driver.findElements(getLocator(elementToken, replacements)));
	}

	protected void _waitForElementToDisappear(String elementToken, String replacement) {
		int i = 0;
		int initTimeout = wait.getTimeout();
		wait.resetImplicitTimeout(2);
		int count;
		while (i <= 20) {
			if (replacement.isEmpty())
				count = elements(elementToken).size();
			else
				count = elements(elementToken, replacement).size();
			if (count == 0)
				break;
			i += 2;
		}
		wait.resetImplicitTimeout(initTimeout);
	}

	protected void waitForElementToDisappear(String elementToken) {
		_waitForElementToDisappear(elementToken, "");
	}

	protected void waitForElementToDisappear(String elementToken, String replacement) {
		_waitForElementToDisappear(elementToken, replacement);
	}


	protected boolean isElementDisplayed(String elementName, String elementTextReplace) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result,
				"TEST FAILED: element '" + elementName + "with text " + elementTextReplace + "' is not displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		Assert.assertEquals(element(elementName).getText().trim(), expectedText,
				"TEST FAILED: element '" + elementName + "' Text is not as expected: ");
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "' is not displayed.");
		return result;
	}

	protected boolean isElementDisplayedByWebElement(WebElement el) {
		wait.waitForElementToBeVisible(el);
		boolean result = el.isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + el + "' is not displayed.");
		return result;
	}

	protected boolean isElementNotDisplayed(String elementName) {
		boolean result;
		result = checkIfElementIsNotThere(elementName);
		assertTrue(result, "Assertion Failed: element '" + elementName + "' is displayed which should not be there");
		return result;
	}

	protected boolean isElementNotDisplayed(String elementName, String elementTextReplace) {
		boolean result;
		try {
			wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
			driver.findElement(getLocator(elementName, elementTextReplace));
			result = false;
		} catch (NoSuchElementException excp) {
			result = true;
		}
		assertTrue(result, "Assertion Failed: element '" + elementName + "' with text " + elementTextReplace
				+ " is displayed as expected");
		return result;
	}

	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		assertTrue(result, "TEST FAILED: element '" + elementName + "' is  ENABLED :- " + !expected);
		return result;
	}

	protected By getLocator(String elementToken, String... replacements) {
		String[] locator = ObjectFileReader.getELementFromFile(this.pageName, elementToken);
		for (String replacement : replacements) {
			locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement);
		}
		return getBy(locator[1].trim(), locator[2].trim());
	}

	public void clickOnFirstLinkBasedOnProvidedText(String elementToken, String linkText) {
		element(elementToken, linkText).click();
	}

	public enum Locators {
		id, name, classname, css, xpath, linktext;
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (locatorType) {
		case "id":
			return By.id(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		case "css":
			return By.cssSelector(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
			return By.className(locatorValue);
		case "linktext":
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	protected WebElement getElement(String elementToken, String replacement1, String replacement2) {
		WebElement elem = null;
		elem = wait
				.waitForElementToBeVisible(driver.findElement(getLocator(elementToken, replacement1, replacement2)));
		return elem;
	}

	protected WebElement getElement(String elementToken) throws NoSuchElementException {
		WebElement elem = null;
		elem = wait.waitForElementToBeVisible(driver.findElement(getLocator(elementToken)));
		return elem;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace1, elementTextReplace2));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "with text " + elementTextReplace1
				+ elementTextReplace2 + "' is not displayed.");
		return result;
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public boolean waitForAValueToAppearForAnElementBasedOnGivenTime(String element, String expectedValue,
			int maxTimeInSeconds) {
		int i;
		boolean flag = false;
		wait.waitForPageToLoadCompletely();

		for (i = 1; i <= maxTimeInSeconds; i++) {
			if (element(element).getText().trim().equalsIgnoreCase(expectedValue)) {
				flag = true;
				break;
			} else {
				wait.hardWait(1);
			}
		}

		return flag;
	}
}
