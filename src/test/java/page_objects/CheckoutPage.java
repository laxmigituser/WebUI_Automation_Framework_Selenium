package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckoutPage extends BasePage{
    public CheckoutPage(){
        super();
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "input#first-name")
    public WebElement firstName;
    @FindBy(css = "input#last-name")
    public WebElement lastName;
    @FindBy(css = "input#postal-code")
    public WebElement postalCode;
    @FindBy(css = "input#continue")
    public WebElement continueButton;
    @FindBy(xpath = "//h3[@data-test='error']")
    public WebElement errorText;

    //checkout-overview details
    @FindBy(css = "div.cart_item")
    public List<WebElement> cartItems;
    @FindBy(css = "button#finish")
    public WebElement finish_button;

    public void enterCheckoutDetails(Optional<String> fname, String lname, String zipcode){
        fname.ifPresent(name -> enterText(firstName, name));
        enterText(lastName, lname);
        enterText(postalCode, zipcode);
        click(continueButton);
        waitForPageLoad();
    }

    public List<String> getItemsInCheckOutOverview(){
        return cartItems.stream().map(item -> item.findElement(By.cssSelector("div.inventory_item_name"))
                                                        .getText()).collect(Collectors.toList());
    }
    public void clickFinish(){
        click(finish_button);
        waitForPageLoad();
    }
    public String getErrorText(){
        return getText(errorText);
    }

}
