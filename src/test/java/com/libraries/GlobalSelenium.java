package com.libraries;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GlobalSelenium {

	private WebDriver driver;
	// private static int waitTimeinSec = 30;
	long waitTimeinSec = 10;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public enum Browser {
		CHROME, FIREFOX, SAFARI, EDGE_CHROMIUM
	}

	public WebDriver startABrowser(Browser browser) {
		try {
			switch (browser) {
			case CHROME:
				driver = startChromeBrowser();
				break;
			case FIREFOX:
				driver = startFirefoxBrowser();
				break;
			case SAFARI:
				driver = startSafariBrowser();
				break;
			case EDGE_CHROMIUM:
				driver = startEdgeBrowser();
				break;

			default:
				System.out.println("currently framework do not support this type of browser");
				driver = startChromeBrowser();
				break;
			}
			driver.manage().deleteAllCookies();

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}

		return driver;

	}

	private void pageSync() {
		customWait(5);
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTimeinSec));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(waitTimeinSec));

	}

	private WebDriver startFirefoxBrowser() {
		try {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			pageSync();
		} catch (Exception e) {

			e.printStackTrace();
			assertEquals(true, false);

		}

		return driver;
	}

	private WebDriver startChromeBrowser() {

		try {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			pageSync();

		} catch (Exception e) {

			e.printStackTrace();
			assertEquals(true, false);
		}
		return driver;
	}

	private WebDriver startSafariBrowser() {
		try {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			pageSync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertEquals(true, false);

		}
		return driver;
	}

	private WebDriver startEdgeBrowser() {
		try {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			pageSync();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}
		return driver;

	}

	public void tearDown() {
		// Thread.sleep(5*100);
		try {
			customWait(10);
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}
	}

	public void highlightwebElement(WebElement element) {
		WrapsDriver wrappedElement = (WrapsDriver) element;
		//
		JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
		for (int i = 1; i < 4; i++) {

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: red; border: 5px solid yellow");
			customWait(1);

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			customWait(2);
		}
	}

	public void handleCheckBox(By by, boolean isChecked) {
		/*
		 * user wants to check the box there is two possibilities box is
		 * checked------->nothing to do or box is empty(not checked) user---->click
		 */

		try {
			WebElement checkBox = driver.findElement(by);
			if (isChecked == true) {
				// WebElement checkBox=null;
				if (checkBox.isSelected()) {

				} else {
					checkBox.click();
				}
			}

			/*
			 * wants to uncheck the box there is also two possibilities box is
			 * checked-->click or box is empty(not checked)-->do nothing
			 */
			else {
				if (checkBox.isSelected()) {
					checkBox.click();
				} else {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}

	}

	public String takeScreenshot(String screenshotName) {
		String finalScreenshotPath;
		finalScreenshotPath = null;

		try {
			String fileLocation = "target/" + screenshotName + ".png";
			File absFilePath = new File(fileLocation);
			String newPath = absFilePath.getAbsolutePath();
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(newPath));
			finalScreenshotPath = newPath;
			System.out.println("screenshot location---->" + newPath);

		} catch (Exception e) {

			e.printStackTrace();
			assertEquals(true, false);

		}

		return finalScreenshotPath;

	}

	public void enterText(By by, String inputString) {
		try {
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(inputString);
		} catch (Exception e) {

			e.printStackTrace();
			assertEquals(true, false);

		}

	}

	public void enterText(By by, Keys keys) {// method overloading
		try {
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(keys);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertEquals(true, false);

		}

	}

	public void selectDropDown(By by, String visibleOptionText) {

		try {
			WebElement dropDownElem = driver.findElement(by);
			Select select = new Select(dropDownElem);
			select.selectByVisibleText(visibleOptionText);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}
	}

	public void selectDropDown(By by, int index) {

		try {
			WebElement dropDownElem = driver.findElement(by);
			Select select = new Select(dropDownElem);
			select.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	public void selectDropDown(String valuestring, By by) {

		try {
			WebElement dropDownElem = driver.findElement(by);
			Select select = new Select(dropDownElem);
			select.selectByValue(valuestring);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}
	/*
	 * public void selectDropDown(By by,int index ) {
	 * 
	 * try { WebElement dropDownElem= driver.findElement(by); Select select = new
	 * Select(dropDownElem); select.selectByIndex(index); } catch (Exception e) {
	 * e.printStackTrace(); assertEquals(true, false); } } 
	 * 
	 * public void selectDropDown(String valueString,By by ) {
	 * 
	 * try { WebElement dropDownElem= driver.findElement(by); Select select = new
	 * Select(dropDownElem); select.selectByValue(valueString); } catch (Exception
	 * e) { e.printStackTrace(); assertEquals(true, false); }
	 */

	public void clickElement(By by) {
		try {
			WebElement element = driver.findElement(by);
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}
	}

	public void clickElement(WebElement element) {// method overloading
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}
	}

	public void hoverOver(By mainMenuBy, By subMenuBy) {
		try {
			WebElement mainMenuElem = driver.findElement(mainMenuBy);

			// moving mouse to main menu
			Actions actions = new Actions(driver);
			actions.moveToElement(mainMenuElem).build().perform();
			customWait(0.5);
			WebElement subMenuElem = driver.findElement(subMenuBy);

			actions.moveToElement(subMenuElem).build().perform();
			subMenuElem.click();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);

		}

	}

	public WebElement waitForElementVisibility(By by) {
		WebElement elem = null;

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeinSec));
			elem = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertEquals(true, false);

		}

		return elem;

	}

	public void customWait(double inSeconds) {
		try {
			// casting /converting data type from Double to long
			long seconds = (long) (inSeconds * 1000);
			Thread.sleep(seconds);

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}

	}

}


