package com.libraries;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.libraries.GlobalSelenium.Browser;

public class Base {

	public WebDriver driver;
	public GlobalSelenium myLibGS = new GlobalSelenium();

	@BeforeClass
	public void beforeAllTest() {
		// this method will run only one time, right before any method in this class
		// runs.
		System.out.println("Running before all tests.... before class");
	}

	@AfterClass
	public void afterAllTest() {
		// this method will run only one time, right after all test/other methods
		// executed.
		System.out.println("Running after all tests.... after class");
	}

	@BeforeMethod
	public void setUp() {

		// driver = myLibGS.startABrowser(Browser.CHROME);
		driver = myLibGS.startABrowser(Browser.CHROME);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			// capture screenshot when test fails
			myLibGS.takeScreenshot(result.getName());
		}

		myLibGS.tearDown();

	}

}
