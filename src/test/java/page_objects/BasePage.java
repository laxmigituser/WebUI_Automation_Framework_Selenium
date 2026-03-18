package page_objects;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(){
        this.driver = DriverFactory.getInstance().getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    protected void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver) .executeScript("return document.readyState") .equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // timeout in seconds
        wait.until(pageLoadCondition);
        //wait for AJAX query to finish. Useful for dynamic web applications using jquery
        // wait.until(d -> (Long) ((JavascriptExecutor) d) .executeScript("return window.jQuery != undefined &&
        // jQuery.active == 0"));
        //For Angular / React based applications different hooks can be used

    }

    protected void click(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    protected void clickUsingJS(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
    protected void clickElement(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(WebElement element, String text){
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    protected String getText(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
    protected boolean elementVisible(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    protected void enterText(WebElement elm, String text){
        wait.until(ExpectedConditions.visibilityOf(elm)).sendKeys(text);
    }

    public String getCurrentPageUrl(){
        return driver.getCurrentUrl();
    }
}
