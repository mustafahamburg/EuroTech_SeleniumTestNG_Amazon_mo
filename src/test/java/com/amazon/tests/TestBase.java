package com.amazon.tests;

import com.amazon.utilities.BrowserUtils;
import com.amazon.utilities.ConfigurationReader;
import com.amazon.utilities.Driver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;

    protected Actions actions;
    protected WebDriverWait wait;

    protected ExtentReports report;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentTest extentLogger;

    @BeforeTest
    public void setUpTest() {

        report = new ExtentReports();

        String projectPath = System.getProperty("user.dir");
        String reportPath = projectPath + "/test-output/report.html";
        // bir usttekini dinamik yaptik asagida.test ler silinmiyor ve kaydedilyor
        // String date=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // String reportPath=projectPath+"/test-output/report"+date+".html";

        htmlReporter = new ExtentHtmlReporter(reportPath);

        report.attachReporter(htmlReporter);

        htmlReporter.config().setReportName("E2E Test");

        report.setSystemInfo("Environment", "Test Amazon");
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Test Engineer", "Umut Ihsan");
        report.setSystemInfo("PO", "Bilal Saglam");

    }

    @AfterTest
    public void tearDownTest() {
        report.flush();
    }
    @BeforeMethod
    public void setUp () {
        driver = Driver.get();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, 10);
    }
    @AfterMethod
    public void tearDown (ITestResult result) throws IOException {
        if(result.getStatus()==ITestResult.FAILURE){
            extentLogger.fail(result.getName());
            String screenShotPath = BrowserUtils.getScreenshot(result.getName());
            extentLogger.addScreenCaptureFromPath(screenShotPath);
            extentLogger.fail(result.getThrowable());
        }
       // Driver.closeDriver();
    }

}
