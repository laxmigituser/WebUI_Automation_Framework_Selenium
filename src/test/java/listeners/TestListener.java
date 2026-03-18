package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentFactory;
import reports.ExtentManager;
import utils.ScreenshotUtils;

import java.util.Arrays;

public class TestListener implements ITestListener {

    static ExtentReports report;
//    static ExtentTest test;
    //keeping test as class variable, is still a shared variable, hence report gets overridden
    public void onStart(ITestContext context) {
        //initialize the report ony once
        if(report == null){
            try {
                report = ExtentManager.setUpExtentReport();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
           }

    public void onTestStart(ITestResult result) {
        //before each TC
//        test = report.createTest(result.getMethod().getMethodName());
//        ExtentFactory.getInstance().setExtent(test);

        // Fetch browser parameter for this test
//        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
//        if (browser != null) {
//            test.info("Browser: " + browser);
//            test.assignCategory(browser);
//        }

        //log test data
//        Object[] params = result.getParameters();
//        if (params != null) {
//            test.info("Test Data: " + Arrays.toString(params));
//        }

        // Create test node
        ExtentTest extentTest = report.createTest(result.getMethod().getMethodName());
        // Store in ThreadLocal
        ExtentFactory.getInstance().setExtent(extentTest);

        // Fetch browser parameter for the test
        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
        if (browser != null) {
            ExtentFactory.getInstance().getExtent().info("Browser: " + browser);
            ExtentFactory.getInstance().getExtent().assignCategory(browser);
        }

        // Log dataset
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            ExtentFactory.getInstance().getExtent().info("Test Data: " + Arrays.toString(params));
        }
    }

    public void onTestSuccess(ITestResult result) {
        ExtentFactory.getInstance().getExtent().log(Status.PASS, "Test case: "+ result.getMethod().getMethodName()+" is passed");
        ExtentFactory.getInstance().removeExtentObject();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Test case: "+ result.getMethod().getMethodName()+" is failed");
        ExtentFactory.getInstance().getExtent().log(Status.FAIL, result.getThrowable());
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshot(result.getName());
            if(screenshotPath != null){
                ExtentFactory.getInstance().getExtent().fail("Test Failed")
                        .addScreenCaptureFromPath(screenshotPath);
            }
             } catch (Exception e) {
            e.printStackTrace();
        }
        ExtentFactory.getInstance().removeExtentObject();
    }

    public void onTestSkipped(ITestResult result) {
        ExtentFactory.getInstance().getExtent().log(Status.SKIP, "Test case: "+ result.getMethod().getMethodName()+" is skipped");
        ExtentFactory.getInstance().removeExtentObject();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {


    }



    public void onFinish(ITestContext context) {
        //only flush once, at end of suite
        if(report != null){
            report.flush();
        }
    }
}
