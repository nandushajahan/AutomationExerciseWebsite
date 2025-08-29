package org.example;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;

import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.JavascriptExecutor;

public class AppTest {
    WebDriver driver;
    private int screenshotCount = 1; // counter for screenshot numbering

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void beforeTest(Method method) {
        System.out.println("Starting Test: " + method.getName());
    }

    @AfterMethod
    public void afterTest() {

        System.out.println("==================================");
    }

    // Reusable screenshot method
    public void takeScreenshot(String testName) {
        try {
        // Create a folder for screenshots if not exists
        File baseDir = new File("screenshots/" + testName);
        if (!baseDir.exists()) {
            baseDir.mkdirs(); // create directories if not already there
        }

        // Capture screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // File name with incremental numbering
        String fileName = "screenshot" + screenshotCount + ".png";
        File destination = new File(baseDir, fileName);

        // Save file
        FileUtils.copyFile(source, destination);

        System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        screenshotCount++;

    } catch (Exception e) {
        System.out.println("Failed to take screenshot: " + e.getMessage());
    }
    }

    @Test
    public void testCase09() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        screenshotCount=1;
        // Navigate to site
        driver.get("https://automationexercise.com/");
        takeScreenshot("testCase09"); // screenshot1
        String title1 = driver.getTitle();
        System.out.println("Page title is: " + title1);
        Assert.assertTrue(title1.contains("Automation"), "Title did not contain 'Automation'");

        // Go to Products page
        WebElement prdctsbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/products']")));
        prdctsbtn.click();
        System.out.println("Navigating to products page");
        takeScreenshot("testCase09"); // screenshot2

        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("products"), "URL did not contain 'products'");
        System.out.println("Page URL is: " + URL);

        // Search for product
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder, 'Search')]")));
        searchBox.sendKeys("shirt");

        WebElement submitSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='submit_search']")));
        System.out.println("Searching for product");
        submitSearch.click();
        takeScreenshot("testCase09"); // screenshot3

        // Count matching products
        List<WebElement> prdcts = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//img[contains(@src, '/get_product_picture')]")));
        int totalPrdcts = prdcts.size();
        System.out.println("Total of " + totalPrdcts + " matching products found!");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2500)");
        takeScreenshot("testCase09"); // screenshot4
    }


@Test
    public void testCase01() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        screenshotCount=1;
        // Navigate to site
        driver.get("https://automationexercise.com/");
        takeScreenshot("testCase01"); // screenshot1
        String title1 = driver.getTitle();
        System.out.println("Page title is: " + title1);
        Assert.assertTrue(title1.contains("Automation"), "Title did not contain 'Automation'");
        // Click on 'Signup / Login' button
        // Locate and click on the "Signup / Login" button
        WebElement signUpLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin.click();
        // Verify 'New User Signup!' is visible
        // Assertion: Check that the "New User Signup!" text is displayed
        takeScreenshot("testCase01");
        WebElement signUpMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='New User Signup!']")));
        Assert.assertTrue(signUpMsg.isDisplayed(),"'New User Signup!' is not visible");
        // Enter name and email address
        // Locate input fields and pass test data
        WebElement nameBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));
        nameBox.sendKeys("testingUser");
        WebElement emailBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']/following-sibling::input[contains(@placeholder,'Email')]")));
        emailBox.sendKeys("testinguser44@gmail.com");
        takeScreenshot("testCase01");
        // Click 'Signup' button
        // Locate and click the "Signup" button
        WebElement signUpBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
        signUpBtn.click();
        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        // Assertion: Validate that the "ENTER ACCOUNT INFORMATION" section appears
        WebElement enterAccInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']")));
        Assert.assertTrue(enterAccInfo.isDisplayed(),"'ENTER ACCOUNT INFORMATION' is not visible");
        takeScreenshot("testCase01"); // screenshot3
        // Fill details: Title, Name, Email, Password, Date of birth
        // Enter personal details and select dropdowns for DOB
        WebElement gender= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'gender1')]")));
        gender.click();
        WebElement pwdBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        pwdBox.sendKeys("testing123");
        WebElement daySelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("days")));
        Select day = new Select(daySelect);
        day.selectByValue("10");
        WebElement monthSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("months")));
        Select month = new Select(monthSelect);
        month.selectByIndex(5);  
        WebElement yearSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("years")));
        Select year = new Select(yearSelect);
        year.selectByValue("1990");
        // Select checkbox 'Sign up for our newsletter!'
        // Locate newsletter checkbox and select it
        WebElement newsletter= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newsletter")));
        newsletter.click();
        takeScreenshot("testCase01"); // screenshot4

        // Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        // Enter additional address and contact details
        WebElement firstNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first_name")));
        firstNameBox.sendKeys("testing");
        WebElement lastNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last_name")));
        lastNameBox.sendKeys("User");
        WebElement companyBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("company")));
        companyBox.sendKeys("TestingCompany");
        WebElement address1Box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address1")));
        address1Box.sendKeys("123 Testing Address");
        WebElement address2Box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address2")));
        address2Box.sendKeys("456 Testing Address 2");
        WebElement countrySelectBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("country")));
        Select country = new Select(countrySelectBox);
        country.selectByVisibleText("Canada");
        WebElement stateBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("state")));
        stateBox.sendKeys("TestingState");
        WebElement cityBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city")));
        cityBox.sendKeys("TestingCity");
        WebElement zipCodeBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("zipcode")));
        zipCodeBox.sendKeys("A1B2C3");
        WebElement mobileNumberBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mobile_number")));
        mobileNumberBox.sendKeys("1234567890");
        takeScreenshot("testCase01"); // screenshot5    
        // Click 'Create Account' button
        // Locate and click "Create Account"
        WebElement createAccBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Create Account']")));
        createAccBtn.click();
        takeScreenshot("testCase01"); // screenshot6
        // Verify that 'ACCOUNT CREATED!' is visible
        // Assertion: Check that confirmation text "ACCOUNT CREATED!" is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed(),"'ACCOUNT CREATED!' is not visible");
        takeScreenshot("testCase01"); // screenshot7
        // Click 'Continue' button  
        // Locate and click "Continue"
        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        continueBtn.click();
        // Verify that 'Logged in as username' is visible
        // Assertion: Validate user is logged in with the correct username
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Logged in as \"]"))).isDisplayed(),"'Logged in as username' is not visible");
        takeScreenshot("testCase01");
        // Click 'Delete Account' button
        // Locate and click "Delete Account"
        WebElement deleteAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Delete Account\"]")));
        deleteAccount.click();
        // Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        // Assertion: Ensure "ACCOUNT DELETED!" message is displayed, then click "Continue"
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Deleted!']"))).isDisplayed(),"\"ACCOUNT DELETED!\" message is not displayed");
        WebElement continueBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        takeScreenshot("testCase01");
        continueBtn2.click();
    }

@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
