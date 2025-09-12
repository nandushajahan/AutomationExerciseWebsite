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

public boolean signUp(String userName, String password)
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
        try //to handle both success and failure scenarios(second signup with same email) 
        {
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
        takeScreenshot("signUp5"); // screenshot5 
        // Click 'Create Account' button
        // Locate and click "Create Account"
        WebElement createAccBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Create Account']")));
        createAccBtn.click();
        takeScreenshot("signUp6"); // screenshot6

        // Verify that 'ACCOUNT CREATED!' is visible
        // Assertion: Check that confirmation text "ACCOUNT CREATED!" is displayed
        try {
            Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed(),"'ACCOUNT CREATED!' is not visible");
            takeScreenshot("signUp success");
            WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Continue']")));
            continueBtn.click();
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Email Address already exist!']")));
            takeScreenshot("Initial signUp failed");
            System.out.println("Sign up failed: " + errorMsg.getText());
            return false;
        }

    }
    catch (Exception e) {
        System.out.println("Sign Up failed: Mostly because this is an existing mail id " + e.getMessage());
        return false;
    }
        
        
        
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

public boolean signIn(String userName, String password) {
    String email = userName + "@gmail.com";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    // Click on 'Signup / Login' button
    WebElement signUpLogin2 = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//i[contains(@class,\"lock\")]")));
    signUpLogin2.click();

    // Verify 'Login to your account' is visible
    WebElement loginMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//h2[text()='Login to your account']")));
    Assert.assertTrue(loginMsg.isDisplayed(), "'Login to your account' is not visible");
    takeScreenshot("signIn1");

    // Enter email and password
    WebElement emailBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//input[@data-qa='login-email']")));
    emailBox2.sendKeys(email);

    WebElement pwdBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//input[@data-qa='login-password']")));
    pwdBox2.sendKeys(password);
    takeScreenshot("signIn2");

    // Click 'Login' button
    WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//button[@data-qa='login-button']")));
    loginBtn.click();

    // Handle success or failure
    try {
        WebElement loggedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()=\" Logged in as \"]")));
        takeScreenshot("SignedInSuccess");
        System.out.println("Login successful: " + loggedIn.getText());
        return true;
    } catch (Exception e) {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[text()='Your email or password is incorrect!']")));
        takeScreenshot("SignInFailed");
        System.out.println("Login failed: " + errorMsg.getText());
        return false;
    }
}

    
// Test Cases

@Test(enabled = false)
public void testCase01() {
    currentTestName = "testCase01";
    screenshotCount = 1;
    gotoHomepage();
    takeScreenshot("gotoHomepage");
    signUp("testedUser12","testing123");
    //deleteAccount();
}


    @Test(enabled = false)
    public void testCase02() {
        currentTestName = "testCase02";
        screenshotCount = 1;
        gotoHomepage();
        takeScreenshot("gotoHomepage");
        signIn("testedUser12","testing123");
        deleteAccount();
    }

    @Test(enabled = false)
    public void testCase03() {
    // Navigate to url 'http://automationexercise.com'
        screenshotCount=1;
        currentTestName = "testCase03";
        gotoHomepage();
        boolean loggedIn = signIn("unknownUser","password123");
        Assert.assertFalse(loggedIn, "Login should fail with invalid credentials");

    }

    @Test(enabled = false)
    public void testCase04() {
        screenshotCount=1;
        currentTestName = "testCase04";
        gotoHomepage();
        signUp("testinguser123c","testing123");
        signIn("testinguser123c","testing123");
        //click logout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/logout']")));
        logoutBtn.click();
         
        // Verify that user is navigated to login page
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/login"), "User is not navigated to login page");
        takeScreenshot("back to home"); // screenshot13
        // Cleanup: Delete the created account
        signIn("testinguser123c","testing123");
        deleteAccount();
    }

    @Test
    public void testCase05() {
    screenshotCount = 1;
    currentTestName = "testCase05";

    // Step 1: Sign up new user
    gotoHomepage();
    boolean firstSignUp = signUp("youagain", "you@gain");
    Assert.assertTrue(firstSignUp, "Initial signup should succeed");

    // Step 2: Logout
    WebElement logoutBtn = new WebDriverWait(driver, Duration.ofSeconds(30))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/logout']")));
    logoutBtn.click();

    // Step 3: Try signing up with existing credentials (should fail)
    gotoHomepage();
    boolean secondSignUp = signUp("youagain", "you@gain");
    Assert.assertFalse(secondSignUp, "Sign up should fail with existing username");

    // Step 4: Cleanup - login and delete account
    boolean loginSuccess = signIn("youagain", "you@gain");
    Assert.assertTrue(loginSuccess, "Sign in should succeed before deleting account");

    deleteAccount();
}


@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
