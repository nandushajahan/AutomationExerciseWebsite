package org.example;

import java.io.File;
import java.io.IOException;
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
    private String currentTestName = "";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void beforeTest(Method method) {
    currentTestName = method.getName();  // e.g., "testCase01"
    screenshotCount = 1;                 // reset counter for each test
}

    @AfterMethod
    public void afterTest() {

        System.out.println("==================================");
    }

    public void takeScreenshot(String stepName) {
    try {
        // Create folder for current test case
        File baseDir = new File("screenshots/" + currentTestName);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        // Capture screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // File name with testCase and step
        String fileName = currentTestName + "_" + stepName + "_" + screenshotCount + ".png";
        File destination = new File(baseDir, fileName);

        // Save screenshot
        FileUtils.copyFile(source, destination);

        System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        screenshotCount++;

    } catch (Exception e) {
        System.out.println("Failed to take screenshot: " + e.getMessage());
    }
}


    @Test(enabled = false)
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

public void gotoHomepage()
{
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    driver.get("https://automationexercise.com/");
    String URL = driver.getCurrentUrl();
    Assert.assertTrue(URL.equals("https://automationexercise.com/"), "URL did not match homepage URL");
    System.out.println("Navigated to homepage, URL is: " + URL);
    takeScreenshot("gotoHomepage"); // screenshot
}

public void signUp(String userName, String password)
{
    String email = userName + "@gmail.com";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    // Click on 'Signup / Login' button
        // Locate and click on the "Signup / Login" button
        WebElement signUpLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin.click();
        // Verify 'New User Signup!' is visible
        // Assertion: Check that the "New User Signup!" text is displayed
        takeScreenshot("signUp1"); // screenshot1
        WebElement signUpMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='New User Signup!']")));
        Assert.assertTrue(signUpMsg.isDisplayed(),"'New User Signup!' is not visible");
        // Enter name and email address
        // Locate input fields and pass test data
        WebElement nameBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));
        nameBox.sendKeys(userName);
        WebElement emailBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']/following-sibling::input[contains(@placeholder,'Email')]")));
        emailBox.sendKeys(email);
        takeScreenshot("signUp2");   // screenshot2
        // Click 'Signup' button
        // Locate and click the "Signup" button
        WebElement signUpBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
        signUpBtn.click();
        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        // Assertion: Validate that the "ENTER ACCOUNT INFORMATION" section appears
        WebElement enterAccInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']")));
        Assert.assertTrue(enterAccInfo.isDisplayed(),"'ENTER ACCOUNT INFORMATION' is not visible");
        takeScreenshot("signUp3"); // screenshot3
        // Fill details: Title, Name, Email, Password, Date of birth
        // Enter personal details and select dropdowns for DOB
        WebElement gender= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'gender1')]")));
        gender.click();
        WebElement pwdBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        pwdBox.sendKeys(password);
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
        takeScreenshot("signUp4"); // screenshot4

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
        takeScreenshot("signUp5"); // screenshot5    done
        // Click 'Create Account' button
        // Locate and click "Create Account"
        WebElement createAccBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Create Account']")));
        createAccBtn.click();
        takeScreenshot("signUp6"); // screenshot6
        // Verify that 'ACCOUNT CREATED!' is visible
        // Assertion: Check that confirmation text "ACCOUNT CREATED!" is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed(),"'ACCOUNT CREATED!' is not visible");
        takeScreenshot("signUp7"); // screenshot7
        // Click 'Continue' button  
        // Locate and click "Continue"
        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        continueBtn.click();
        
}

public void deleteAccount()
{
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    // Click 'Delete Account' button
        // Locate and click "Delete Account"
        WebElement deleteAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Delete Account\"]")));
        deleteAccount.click();
        // Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        // Assertion: Ensure "ACCOUNT DELETED!" message is displayed, then click "Continue"
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Deleted!']"))).isDisplayed(),"\"ACCOUNT DELETED!\" message is not displayed");
        WebElement continueBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        takeScreenshot("deleteAccount"); // screenshot
        continueBtn2.click();
}

    
// Test Cases

@Test
public void testCase01() {
    currentTestName = "testCase01";
    screenshotCount = 1;
    gotoHomepage();
    takeScreenshot("gotoHomepage");
    signUp("testedUser12","testing123");
    deleteAccount();
}


    @Test
    public void testCase02() {
        currentTestName = "testCase01";
        screenshotCount = 1;
        gotoHomepage();
        takeScreenshot("gotoHomepage");

        // Placeholder for Test Case 02
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        screenshotCount=1;
        // Navigate to site
        driver.get("https://automationexercise.com/");
        takeScreenshot("testCase02"); // screenshot1
        //Click on 'Signup / Login' button
        WebElement signUpLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin.click();
        // perform signup with new user
        // Verify 'New User Signup!' is visible
        // Assertion: Check that the "New User Signup!" text is displayed
        takeScreenshot("testCase02"); // screenshot2
        WebElement signUpMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='New User Signup!']")));
        Assert.assertTrue(signUpMsg.isDisplayed(),"'New User Signup!' is not visible");
        // Enter name and email address
        // Locate input fields and pass test data
        WebElement nameBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));
        nameBox.sendKeys("testingUser");
        WebElement emailBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']/following-sibling::input[contains(@placeholder,'Email')]")));
        emailBox.sendKeys("testinguser44@gmail.com");
        takeScreenshot("testCase02");   // screenshot3
        // Click 'Signup' button
        // Locate and click the "Signup" button
        WebElement signUpBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
        signUpBtn.click();
        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        // Assertion: Validate that the "ENTER ACCOUNT INFORMATION" section appears
        WebElement enterAccInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']")));
        Assert.assertTrue(enterAccInfo.isDisplayed(),"'ENTER ACCOUNT INFORMATION' is not visible");
        takeScreenshot("testCase02"); // screenshot4
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
        takeScreenshot("testCase02"); // screenshot5

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
        takeScreenshot("testCase02"); // screenshot6    
        // Click 'Create Account' button
        // Locate and click "Create Account"
        WebElement createAccBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Create Account']")));
        createAccBtn.click();
        takeScreenshot("testCase02"); // screenshot7
        // Verify that 'ACCOUNT CREATED!' is visible
        // Assertion: Check that confirmation text "ACCOUNT CREATED!" is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed(),"'ACCOUNT CREATED!' is not visible");
        // Click 'Continue' button  
        // Locate and click "Continue"
        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        continueBtn.click();
        // Verify that 'Logged in as username' is visible
        // Assertion: Validate user is logged in with the correct username
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Logged in as \"]"))).isDisplayed(),"'Logged in as username' is not visible");
        takeScreenshot("testCase02"); // screenshot8
        //click logout
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/logout']")));
        logoutBtn.click();
        //Click on 'Signup / Login' button
        WebElement signUpLogin2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin2.click();
        //Verify 'Login to your account' is visible
        WebElement loginMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Login to your account']")));
        Assert.assertTrue(loginMsg.isDisplayed(),"'Login to your account' is not visible");
        takeScreenshot("testCase02"); // screenshot9
        //Enter  correct email address and password
        WebElement emailBox2= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));
        emailBox2.sendKeys("testinguser44@gmail.com");
        WebElement pwdBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-password']")));
        pwdBox2.sendKeys("testing123");
        takeScreenshot("testCase02"); // screenshot10
        //Click 'Login' button
        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-qa='login-button']")));
        loginBtn.click();
        //Verify that 'Logged in as username' is visible
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Logged in as \"]"))).isDisplayed(),"'Logged in as username' is not visible");
        //Click 'Delete Account' button
        WebElement deleteAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Delete Account\"]")));
        deleteAccount.click();
        //Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Deleted!']"))).isDisplayed(),"\"ACCOUNT DELETED!\" message is not displayed");
        WebElement continueBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        takeScreenshot("testCase02"); // screenshot11
        continueBtn2.click();
    }

    @Test(enabled = false)
    public void testCase03() {
    // Navigate to url 'http://automationexercise.com'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        screenshotCount=1;
        // Navigate to site
        driver.get("https://automationexercise.com/");
        takeScreenshot("testCase03"); // screenshot1
        String title1 = driver.getTitle();
        System.out.println("Page title is: " + title1);
        // Verify that home page is visible successfully
        Assert.assertTrue(title1.contains("Automation"), "Title did not contain 'Automation'");
    
    // Click on 'Signup / Login' button
        WebElement signUpLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin.click();
    // Verify 'Login to your account' is visible
        WebElement loginMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Login to your account']")));
        Assert.assertTrue(loginMsg.isDisplayed(),"'Login to your account' is not visible");
        takeScreenshot("testCase03"); // screenshot2
    // Enter incorrect email address and password
        WebElement emailBox2= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));
        emailBox2.sendKeys("betb@gmail.com");
        WebElement pwdBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-password']")));
        pwdBox2.sendKeys("betb123");
        takeScreenshot("testCase03"); // screenshot3
    // Click 'login' button
        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-qa='login-button']")));
        loginBtn.click();
        takeScreenshot("testCase03"); // screenshot4
    // Verify error 'Your email or password is incorrect!' is visible
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your email or password is incorrect!']")));
        Assert.assertTrue(errorMsg.isDisplayed(),"'Your email or password is incorrect!' is not visible");
        takeScreenshot("testCase03"); // screenshot5
    }

    @Test(enabled = false)
    public void testCase04() {
    // Navigate to url 'http://automationexercise.com'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        screenshotCount=1;
        // Navigate to site
        driver.get("https://automationexercise.com/");
        takeScreenshot("testCase04"); // screenshot1
        String title1 = driver.getTitle();
        System.out.println("Page title is: " + title1);
    // Verify that home page is visible successfully
        Assert.assertTrue(title1.contains("Automation"), "Title did not contain 'Automation'");
    // Click on 'Signup / Login' button
        WebElement signUpLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin.click();
    // Verify 'New User Signup!' is visible
        WebElement signUpMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='New User Signup!']")));
        takeScreenshot("testCase04"); // screenshot2
        Assert.assertTrue(signUpMsg.isDisplayed(),"'New User Signup!' is not visible");
        // Enter name and email address
        // Locate input fields and pass test data
        WebElement nameBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));
        nameBox.sendKeys("testingUser");
        WebElement emailBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']/following-sibling::input[contains(@placeholder,'Email')]")));
        emailBox.sendKeys("testinguser44@gmail.com");
        takeScreenshot("testCase04");   // screenshot3
        // Click 'Signup' button
        // Locate and click the "Signup" button
        WebElement signUpBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
        signUpBtn.click();
        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        // Assertion: Validate that the "ENTER ACCOUNT INFORMATION" section appears
        WebElement enterAccInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']")));
        Assert.assertTrue(enterAccInfo.isDisplayed(),"'ENTER ACCOUNT INFORMATION' is not visible");
        takeScreenshot("testCase04"); // screenshot4
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
        takeScreenshot("testCase04"); // screenshot5

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
        takeScreenshot("testCase04"); // screenshot6    
        // Click 'Create Account' button
        // Locate and click "Create Account"
        WebElement createAccBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Create Account']")));
        createAccBtn.click();
        takeScreenshot("testCase04"); // screenshot7
        // Verify that 'ACCOUNT CREATED!' is visible
        // Assertion: Check that confirmation text "ACCOUNT CREATED!" is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed(),"'ACCOUNT CREATED!' is not visible");
        // Click 'Continue' button  
        // Locate and click "Continue"
        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        continueBtn.click();
        // Verify that 'Logged in as username' is visible
        // Assertion: Validate user is logged in with the correct username
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Logged in as \"]"))).isDisplayed(),"'Logged in as username' is not visible");
        takeScreenshot("testCase04"); // screenshot8
        //click logout
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/logout']")));
        logoutBtn.click();
        //Click on 'Signup / Login' button
        WebElement signUpLogin2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin2.click();

    // Verify 'Login to your account' is visible
        WebElement loginMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Login to your account']")));
        Assert.assertTrue(loginMsg.isDisplayed(),"'Login to your account' is not visible");
        takeScreenshot("testCase04"); // screenshot9
    // Enter correct email address and password
        WebElement emailBox2= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));
        emailBox2.sendKeys("testinguser44@gmail.com"); // provide email
        WebElement pwdBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-password']")));
        pwdBox2.sendKeys("testing123"   ); // provide password
        takeScreenshot("testCase04"); // screenshot10   
    // Click 'login' button
        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-qa='login-button']")));
        loginBtn.click();
        takeScreenshot("testCase04"); // screenshot11
    // Verify that 'Logged in as username' is visible
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Logged in as \"]"))).isDisplayed(),"'Logged in as username' is not visible");
        takeScreenshot("testCase04"); // screenshot12 
    // Click 'Logout' button
        WebElement logoutBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/logout']")));
        logoutBtn2.click();
    // Verify that user is navigated to login page
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/login"), "User is not navigated to login page");
        takeScreenshot("testCase04"); // screenshot13
    // Perform cleanup by deleting the created account
        WebElement signUpLogin3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin3.click();
        WebElement emailBox3= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));
        emailBox3.sendKeys("testinguser44@gmail.com"); // provide email
        WebElement pwdBox3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-password']")));
        pwdBox3.sendKeys("testing123"   ); // provide password
        WebElement loginBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-qa='login-button']")));
        loginBtn2.click();
        WebElement deleteAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Delete Account\"]")));
        deleteAccount.click();
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Deleted!']"))).isDisplayed(),"\"ACCOUNT DELETED!\" message is not displayed");
        WebElement continueBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        takeScreenshot("testCase04"); // screenshot14
        continueBtn2.click();   
    }

    @Test(enabled = false)
    public void testCase05() {
    // Navigate to url 'http://automationexercise.com'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        screenshotCount=1;
        // Navigate to site
        driver.get("https://automationexercise.com/");
        takeScreenshot("testCase05"); // screenshot1
        String title1 = driver.getTitle();
        System.out.println("Page title is: " + title1); 
    // Verify that home page is visible successfully
        Assert.assertTrue(title1.contains("Automation"), "Title did not contain 'Automation'");
    // Click on 'Signup / Login' button
        WebElement signUpLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin.click();
    // Verify 'New User Signup!' is visible
        WebElement signUpMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='New User Signup!']")));
        takeScreenshot("testCase05"); // screenshot2
        Assert.assertTrue(signUpMsg.isDisplayed(),"'New User Signup!' is not visible");
        WebElement nameBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));
        nameBox.sendKeys("testingUser");
        WebElement emailBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']/following-sibling::input[contains(@placeholder,'Email')]")));
        emailBox.sendKeys("testinguser44@gmail.com");
        takeScreenshot("testCase05");   // screenshot3
        // Click 'Signup' button
        // Locate and click the "Signup" button
        WebElement signUpBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
        signUpBtn.click();
        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        // Assertion: Validate that the "ENTER ACCOUNT INFORMATION" section appears
        WebElement enterAccInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']")));
        Assert.assertTrue(enterAccInfo.isDisplayed(),"'ENTER ACCOUNT INFORMATION' is not visible");
        takeScreenshot("testCase05"); // screenshot4
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
        takeScreenshot("testCase05"); // screenshot5

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
        takeScreenshot("testCase05"); // screenshot6    
        // Click 'Create Account' button
        // Locate and click "Create Account"
        WebElement createAccBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Create Account']")));
        createAccBtn.click();
        takeScreenshot("testCase05"); // screenshot7
        // Verify that 'ACCOUNT CREATED!' is visible
        // Assertion: Check that confirmation text "ACCOUNT CREATED!" is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed(),"'ACCOUNT CREATED!' is not visible");
        // Click 'Continue' button  
        // Locate and click "Continue"
        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        continueBtn.click();
        // Click 'Logout' button
        WebElement logoutBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/logout']")));
        logoutBtn2.click();
        // Click on 'Signup / Login' button
        WebElement signUpLogin2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin2.click();
    // Enter name and already registered email address
        WebElement nameBox2= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));
        nameBox2.sendKeys("testingUser");
        WebElement emailBox2= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']/following-sibling::input[contains(@placeholder,'Email')]")));
        emailBox2.sendKeys("testinguser44@gmail.com"); // provide already registered email
    // Click 'Signup' button
        WebElement signUpBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
        signUpBtn2.click();
        takeScreenshot("testCase05"); // screenshot8
    // Verify error 'Email Address already exist!' is visible
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Email Address already exist!']")));
        Assert.assertTrue(errorMsg.isDisplayed(),"'Email Address already exist!' is not visible");
        takeScreenshot("testCase05"); // screenshot9

        // Perform cleanup by deleting the created account
        WebElement signUpLogin3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,\"lock\")]")));
        signUpLogin3.click();
        WebElement emailBox3= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));
        emailBox3.sendKeys("testinguser44@gmail.com"); // provide email
        WebElement pwdBox3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-password']")));
        pwdBox3.sendKeys("testing123"   ); // provide password
        WebElement loginBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-qa='login-button']")));
        loginBtn2.click();
        WebElement deleteAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\" Delete Account\"]")));
        deleteAccount.click();
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Deleted!']"))).isDisplayed(),"\"ACCOUNT DELETED!\" message is not displayed");
        WebElement continueBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
        takeScreenshot("testCase05"); // screenshot10
        continueBtn2.click();   
    }

@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
