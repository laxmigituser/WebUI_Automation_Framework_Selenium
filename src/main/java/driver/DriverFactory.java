package driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private DriverFactory() {}
    private static DriverFactory instance = new DriverFactory();
    //this method will return instance of current class, we need Cz we created the private constructor.
    // so obj can not be created directly
    public static DriverFactory getInstance() {
        return instance;
    }
    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public WebDriver getDriver(){
        return driver.get();
    }
    public void setDriver(WebDriver driver_instance){
        driver.set(driver_instance);
    }
    public void quitDriver(){
        driver.get().quit();
        driver.remove();
    }
}
