package base;

import driver.BrowserFactory;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import page_objects.PageObjectManager;
import utils.CaptureNetwork;
import utils.JsonUtils;
import utils.NetworkInterceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class TestBase {
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser, Method method) throws IOException {
       String browsername = (System.getProperty("browser")!=null) ? System.getProperty("browser") : browser ;
        WebDriver driver_obj = BrowserFactory.createDriver(browsername);
        DriverFactory.getInstance().setDriver(driver_obj);
        WebDriver driver = DriverFactory.getInstance().getDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        //captures all network calls and logs to report
        //NetworkInterceptor.startCapture(driver);

        //Capture Network calls of tests with @CaptureNetwork
        //if (method.isAnnotationPresent(CaptureNetwork.class)) {
        //    NetworkInterceptor.start(driver);
        //}
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        PageObjectManager.remove();
        DriverFactory.getInstance().quitDriver();
    }
}
