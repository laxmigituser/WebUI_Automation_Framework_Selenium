package page_objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    public LoginPage(){
        super();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input#user-name")
    public WebElement userName;
    @FindBy(css = "input#password")
    public WebElement userPassword;
    @FindBy(css = "input#login-button")
    public WebElement loginButton;
    @FindBy(xpath = "//h3[@data-test='error']")
    public WebElement loginErrorText;

    public void navigateTLoginPage(){
        driver.get("https://www.saucedemo.com/");
        waitForPageLoad();
    }

    public void loginToApplication(String username, String password){
        type(userName, username);
        type(userPassword, password);
        click(loginButton);
        waitForPageLoad();
    }
    public boolean isErrorVisible(){
       return elementVisible(loginErrorText);
    }
    public String getLoginErrorText(){
        return getText(loginErrorText);
    }
}
