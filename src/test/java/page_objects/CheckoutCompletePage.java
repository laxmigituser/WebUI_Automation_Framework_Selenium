package page_objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CheckoutCompletePage extends BasePage{
    public CheckoutCompletePage(){
        super();
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "img.pony_express")
    public WebElement success_image;
    @FindBy(css = "h2.complete-header")
    public WebElement completeHeader;
    @FindBy(css = "div.complete-text")
    public WebElement completeText;
    @FindBy(css = "button#back-to-products")
    public WebElement backToHomeButton;

    public void verifyOrderCompleteDetails(){
        Assert.assertTrue(elementVisible(success_image));
        Assert.assertTrue(elementVisible(completeHeader));
        Assert.assertTrue(elementVisible(completeText));
        Assert.assertTrue(elementVisible(backToHomeButton));
        Assert.assertEquals(getText(completeHeader), "Thank you for your order!", "Complete Header text is not matching");
        Assert.assertEquals(getText(completeText), "Your order has been dispatched, and will arrive just as fast as the pony can get there!", "Complete text is not matching");
    }

}
