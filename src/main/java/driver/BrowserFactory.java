package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {
    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
//                System.setProperty("webdriver.chrome.driver",
//                        System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
//                Map<String, Object> prefs = new HashMap<>();
//                prefs.put("credentials_enable_service", false);
//                prefs.put("profile.password_manager_enabled", false);
//                options.setExperimentalOption("prefs", prefs);
//                options.addArguments("--disable-notifications");
//                options.addArguments("--disable-infobars");
                options.addArguments("--incognito");
//                options.addArguments("--disable-features=PasswordLeakDetection");
                //for headless execution // mvn clean test -PRegression -Dheadless=true
                String headless = System.getProperty("headless");
                if ("true".equalsIgnoreCase(headless)) {
                    options.addArguments("--headless=new");  // modern headless
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");
                }

                return new ChromeDriver(options);
            case "firefox":
                return new FirefoxDriver();
            case "edge":
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Invalid browser");
        }
    }
}
